package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.constants.message.UserVerificationMessage;
import com.accountmanagement.enums.UserType;
import com.accountmanagement.exceptions.AccountLockException;
import com.accountmanagement.exceptions.BusinessException;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.InvalidRefreshKeyException;
import com.accountmanagement.exceptions.InvalidRequestException;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.RefreshKeyExpiredException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.Organization;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.model.UserVerification;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.repository.UserVerificationRepository;
import com.accountmanagement.request.ChangeTemporaryPasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.ResendEmailVerificationRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.UserUpdationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@Service
public class UserService {

    private final OrganizationService organizationService;

    private final RedisService redisService;

    private final OtpService otpService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenUtility tokenUtility;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final UserSessionService userSessionService;

    private final UserSessionRepository userSessionRepository;

    private final UserMapper userMapper;

    private final EmailQueueService emailQueueService;

    private final OrganizationRepository organizationRepository;

    private final UserVerificationService userVerificationService;

    private final UserVerificationRepository userVerificationRepository;

    UserService(OtpService otpService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenUtility tokenUtility,
            UserRepository userRepository, UserProfileRepository userProfileRepository,
            UserLoginAuditLogService userLoginAuditLogService,
            UserSessionService userSessionService, UserSessionRepository userSessionRepository, UserMapper userMapper,
            EmailQueueService emailQueueService,
            OrganizationRepository organizationRepository,
            UserVerificationService userVerificationService, UserVerificationRepository userVerificationRepository,
            RedisService redisService, OrganizationService organizationService) {
        this.otpService = otpService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenUtility = tokenUtility;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userLoginAuditLogService = userLoginAuditLogService;
        this.userSessionService = userSessionService;
        this.userSessionRepository = userSessionRepository;
        this.userMapper = userMapper;
        this.emailQueueService = emailQueueService;
        this.organizationRepository = organizationRepository;
        this.userVerificationService = userVerificationService;
        this.userVerificationRepository = userVerificationRepository;
        this.redisService = redisService;
        this.organizationService = organizationService;
    }

    @Transactional
    public String registerUser(UserRegistrationRequest request) {
        Organization organization = validateOrganization(request.getOrganizationId());
        organizationService.validateOrganizationAccess(organization.getId());
        validateUser(request);
        String email = getRegistrationEmail(request, organization);
        User user = userMapper.toRegisterUser(request, email);
        User savedUser = userRepository.save(user);
        UserProfile userProfile = userMapper.toRegisterUserProfile(savedUser.getId(), request);
        userProfileRepository.save(userProfile);
        String token = generateVerificationToken();
        saveVerificationToken(token, user.getId());
        String verificationLink = UserMessage.EMAIL_VERIFICATION_LINK + token;
        sendVerificationEmail(savedUser, verificationLink);
        userVerificationService.completUserRegistration(savedUser.getId());
        return UserMessage.SEND_EMAIL_VERIFICATION_LINK;
    }

    @Transactional
    public void verifyEmail(String token) {
        String userId = getUserIdFromToken(token);
        User user = findUser(UUID.fromString(userId));
        UserVerification verification = findUserVerification(user.getId());
        updateEmailVerification(verification);
        deleteVerificationToken(token);
    }

    @Transactional
    public void resendVerificationEmail(ResendEmailVerificationRequest resendEmailVerificationRequest) {
        validateEmailNotVerified(resendEmailVerificationRequest.getUserId());
        User user = findByEmail(resendEmailVerificationRequest.getEmail());
        validateUserAndEmail(resendEmailVerificationRequest.getUserId(), user.getId());
        String token = generateVerificationToken();
        saveVerificationToken(token, user.getId());
        String verificationLink = UserMessage.EMAIL_VERIFICATION_LINK + token;
        sendVerificationEmail(user, verificationLink);
    }

    @Transactional
    public String loginUser(LoginRequest loginRequest) {
        User user = getUser(loginRequest.getLogin());
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        validateAccountStatus(userVerification);
        validatePassword(loginRequest.getPassword(), user, userVerification);
        resetFailedLoginAttempts(userVerification);
        validatePasswordResetStatus(userVerification);
        String otp = otpService.generateOtp();
        userSessionService.createUserSession(user.getId(), otp);
        emailQueueService.addToLoginQueue(user.getId(),
                user.getEmail(), otp);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(),
                user.getId(), AppConstants.LOGIN, AppConstants.SUCCESS);
        return UserMessage.OTP;
    }

    public ApiResponse verifyLoginOtp(VerifyOtpRequest verifyOtpRequest) {
        User user = findByEmail(verifyOtpRequest.getEmail());
        otpService.verifyOtp(user.getId(), verifyOtpRequest.getOtp());
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        String accessToken = tokenUtility.generateJwt(user.getUserName());
        completeUserOnboarding(userVerification);
        String refreshKey = UUID.randomUUID().toString();
        saveAccessToken(accessToken, user.getId());
        userSessionService.updateSessionAfterOtp(user.getId(), refreshKey);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Verify Otp",
                AppConstants.SUCCESS);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP_VERIFY, 200);
        response.setAccessToken(accessToken);
        response.setRefreshKey(refreshKey);
        return response;
    }

    @Transactional
    public User updateUser(UUID id, UserUpdationRequest request) {
        User user = findActiveUser(id);
        User newuser = userMapper.toUpdateUser(user, request);
        User savedUser = userRepository.save(newuser);
        UserProfile userProfile = findUserProfileByUserId(id);
        UserProfile updatedUserProfile = userMapper.toUpdateUserProfile(userProfile, request);
        userProfileRepository.save(updatedUserProfile);
        return savedUser;
    }

    public void deleteUserById(UUID id) {
        User user = findById(id);
        user.setStatus(AppConstants.INACTIVE);
        userRepository.save(user);
        UserProfile userProfile = findUserProfileByUserId(id);
        userProfile.setStatus(AppConstants.INACTIVE);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void changeTemporaryPassword(ChangeTemporaryPasswordRequest request) {
        User user = getUserByResetToken(request.getResetToken());
        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {
            throw new BusinessException(
                    UserMessage.PASSWORD_MISMATCH);
        }
        user.setPassword(bCryptPasswordEncoder.encode(
                request.getNewPassword()));
        userRepository.save(user);
        UserVerification verification = findByVerificationByUserId(user.getId());
        verification.setIsPasswordResetCompleted(true);
        userVerificationRepository.save(verification);
        redisService.delete(
                "PASSWORD-RESET:" + request.getResetToken());
    }

    private User getUserByResetToken(String resetToken) {
        String redisKey = "PASSWORD-RESET:" + resetToken;
        String userId = redisService.get(redisKey);
        if (userId == null) {
            throw new BusinessException("Invalid or expired reset token");
        }
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RecordNotFoundException(UserMessage.USER_NOT_FOUND));
    }

    public String generateAccessToken(String refreshKey) {
        UserSession userSession = userSessionRepository.findByRefreshKey(refreshKey)
                .orElseThrow(() -> new RecordNotFoundException("refresh key not found"));
        if (!Boolean.TRUE.equals(userSession.getRefreshKeyStatus())) {
            throw new InvalidRefreshKeyException("Refresh key invalid");
        }
        if (userSession.getRefreshKeyExpiration().isBefore(LocalDateTime.now())) {
            throw new RefreshKeyExpiredException("Refresh key expired.Please login again");
        }
        if ("Logout".equalsIgnoreCase(userSession.getSessionStatus())) {
            throw new InvalidSessionException("Please login again");
        }
        User user = userRepository.findById(userSession.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
        String newToken = tokenUtility.generateJwt(user.getUserName());
        String key = AppConstants.ACCESS_TOKEN + newToken;
        redisService.save(key, user.getId().toString(), 10, TimeUnit.MINUTES);
        return newToken;
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new RecordNotFoundException(UserMessage.USER_NOT_FOUND);
        }
        return users;
    }

    public String signout(String authHeader) {
        User user = Apputility.getLoggedUser();
        String accessToken = authHeader.replace("Bearer", "").trim();
        UserSession userSession = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new RecordNotFoundException("user session not found"));
        userSession.setSessionStatus("logout");
        userSession.setRefreshKey(null);
        userSession.setRefreshKeyStatus(false);
        userSession.setIsValidToken(false);
        redisService.delete(accessToken);
        userSessionRepository.save(userSession);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "logout", "success");
        return "user logout successfully";
    }

    private String getRegistrationEmail(UserRegistrationRequest request, Organization organization) {
        if (request.getUserType() == UserType.SUPERADMIN) {
            return organization.getContactEmail();
        }
        return request.getEmail();
    }

    private String getUserIdFromToken(String token) {
        String redisKey = AppConstants.EMAIL_VERIFICATION + token;
        String userId = redisService.get(redisKey);
        if (userId == null) {
            throw new RecordNotFoundException(UserVerificationMessage.INVALID_LINK);
        }
        return userId;
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException(UserMessage.USER_NOT_FOUND));
    }

    private UserVerification findUserVerification(UUID userId) {
        return userVerificationRepository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException(UserVerificationMessage.VERIFICATION_RECORD_NOT_FOUND));
    }

    private void updateEmailVerification(
            UserVerification verification) {
        verification.setIsEmailVerified(true);
        verification.setProfileCompletedPercentage(25);
        userVerificationRepository.save(verification);
    }

    private void deleteVerificationToken(String token) {
        String redisKey = AppConstants.EMAIL_VERIFICATION + token;
        redisService.delete(redisKey);
    }

    private void validateEmailNotVerified(UUID userId) {
        UserVerification userVerification = userVerificationRepository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException(
                        UserVerificationMessage.VERIFICATION_RECORD_NOT_FOUND));
        if (Boolean.TRUE.equals(userVerification.getIsEmailVerified())) {
            throw new RuntimeException(UserVerificationMessage.EMAIL_VERIFIED);
        }
    }

    private void validateUserAndEmail(UUID userId, UUID userEmail) {
        if (!userId.equals(userEmail)) {
            throw new RuntimeException(
                    "User Id And Email Do Not Match");
        }
    }

    private String generateVerificationToken() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }

    private UserVerification findByVerificationByUserId(UUID id) {
        return userVerificationRepository.findByUserId(id)
                .orElseThrow(() -> new RecordNotFoundException("User id not found"));
    }

    private void saveVerificationToken(String token, UUID userId) {
        String key = AppConstants.EMAIL_VERIFICATION + token;
        redisService.save(key, userId.toString(), 10, TimeUnit.MINUTES);
    }

    private void sendVerificationEmail(User user, String verificationLink) {
        emailQueueService.addToEmailVerificationQueue(user.getId(), user.getEmail(), verificationLink);
    }

    public User findByOrganizationId(UUID organizationId) {
        return userRepository.findByOrganizationId(organizationId)
                .orElseThrow(() -> new RecordNotFoundException("Organization Id Not Found"));
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User id not found"));
    }

    public UserProfile findUserProfileByUserId(UUID userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RecordNotFoundException("User id not found"));
    }

    public void validateUser(UserRegistrationRequest request) {
        validateOrganizationId(request.getOrganizationId());
        validateUserName(request.getUserName());
        validateContactNumber(request.getContactNumber());
        validateEmail(request.getEmail());
    }

    public Organization validateOrganization(UUID id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Organization not found"));
    }

    private void validateOrganizationId(UUID organizationId) {
        if (!organizationRepository.existsById(organizationId)) {
            throw new RecordNotFoundException("Organization id not found.");
        }
    }

    private void validateUserName(String userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new UserAlreadyExistsException("Username already exists.");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Email already exists.");
        }
    }

    private void validateContactNumber(String contactNumber) {
        if (userRepository.existsByContactNumber(contactNumber)) {
            throw new UserAlreadyExistsException("Contact number already exists.");
        }
    }

    private User getUser(String login) {
        return userRepository.findByUserNameOrEmailOrContactNumber(login, login, login)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(UserMessage.USER_EMAIL_NOT_FOUND));
    }

    private void validateAccountStatus(UserVerification user) {
        if (!Boolean.TRUE.equals(user.getIsAccountLocked())) {
            return;
        }
        if (user.getLockedTime() != null &&
                LocalDateTime.now().isBefore(user.getLockedTime().plusMinutes(30))) {
            throw new AccountLockException(UserMessage.ACCOUNT_LOCKED);
        }
        user.setIsAccountLocked(false);
        user.setFailedLoginAttempts(0);
        user.setLockedTime(null);
        userVerificationRepository.save(user);
    }

    private void validatePassword(String password, User user, UserVerification userVerification) {
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return;
        }
        userVerificationService.updateFailedLoginAttempt(userVerification);
        int attempts = userVerification.getFailedLoginAttempts();
        if (attempts >= 3) {
            userLoginAuditLogService.createUserLog(user.getOrganizationId(),
                    user.getId(), "Login", UserMessage.LOCKED);
            throw new AccountLockException("Account locked due to 3 failed login attempts");
        }
        int remainingAttempts = 3 - attempts;
        userLoginAuditLogService.createUserLog(user.getOrganizationId(),
                user.getId(), "Login", "failed");
        throw new InvalidCredentialsException(
                "Invalid credentials. " + remainingAttempts + " attempt" + (remainingAttempts > 1 ? "s" : "")
                        + " remaining.");
    }

    private void resetFailedLoginAttempts(UserVerification userVerification) {
        if (userVerification.getFailedLoginAttempts() == 0 &&
                !Boolean.TRUE.equals(userVerification.getIsAccountLocked())) {
            return;
        }
        userVerification.setFailedLoginAttempts(0);
        userVerification.setIsAccountLocked(false);
        userVerification.setLockedTime(null);
        userVerificationRepository.save(userVerification);
    }

    public User findByOrganizationIdAndUserType(UUID organizationId, UserType superadmin) {
        return userRepository.findByOrganizationIdAndUserType(organizationId, UserType.SUPERADMIN)
                .orElseThrow(() -> new RecordNotFoundException("Organization id and user type not found"));
    }

    private User findActiveUser(UUID id) {
        User user = findById(id);
        if (AppConstants.INACTIVE.equalsIgnoreCase(user.getStatus())) {
            throw new InvalidRequestException(UserMessage.USER_INACTIVE);
        }
        return user;
    }

    private void completeUserOnboarding(UserVerification verification) {
        if (!Boolean.TRUE.equals(verification.getIsUserOnboarded())) {
            verification.setIsUserOnboarded(true);
            userVerificationRepository.save(verification);
        }
    }

    private void saveAccessToken(String token, UUID userId) {
        String key = AppConstants.ACCESS_TOKEN + token;
        redisService.save(key, userId.toString(), 10, TimeUnit.MINUTES);
    }

    private void validatePasswordResetStatus(UserVerification userVerification) {
        if (!Boolean.TRUE.equals(
                userVerification.getIsPasswordResetCompleted())) {
            throw new InvalidRequestException(
                    "Please change your temporary password before login");
        }
    }

    @Transactional
    public void createTemporaryCredentials(UUID userId) {
        User user = findById(userId);
        String temporaryPassword = Apputility.generateTemporaryPassword();
        user.setPassword(bCryptPasswordEncoder.encode(temporaryPassword));
        userRepository.save(user);
        String resetToken = UUID.randomUUID().toString().replaceAll("-", "");
        String redisKey = "PASSWORD-RESET:" + resetToken;
        redisService.save(redisKey, user.getId().toString(), 10, TimeUnit.MINUTES);
        String changePasswordLink = UserMessage.TEMPORARY_PASSWORD_CHANGE_LINK + resetToken;
        emailQueueService.addTemporaryPasswordEmail(user.getId(), user.getEmail(),
                user.getUserName(),
                temporaryPassword, changePasswordLink);
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        userVerification.setIsPasswordResetCompleted(false);
        userVerificationRepository.save(userVerification);
    }
}
