package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.exceptions.AccountLockException;
import com.accountmanagement.exceptions.BusinessException;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.InvalidOtpException;
import com.accountmanagement.exceptions.InvalidRefreshKeyException;
import com.accountmanagement.exceptions.InvalidRequestException;
import com.accountmanagement.exceptions.InvalidSessionException;
import com.accountmanagement.exceptions.MaxOtpAttemptException;
import com.accountmanagement.exceptions.OtpExpiredException;
import com.accountmanagement.exceptions.OtpNotFoundException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.RefreshKeyExpiredException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.EmailQueue;
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
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.UserUpdationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@Service
public class UserService {

    private final OrganizationService organizationService;

    private final OtpService otpService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenUtility tokenUtility;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final UserSessionService userSessionService;

    private final UserSessionRepository userSessionRepository;

    private final UserMapper userMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private final EmailQueueService emailQueueService;

    private final OrganizationRepository organizationRepository;

    private final UserVerificationService userVerificationService;

    private final UserVerificationRepository userVerificationRepository;

    UserService(OtpService otpService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenUtility tokenUtility,
            UserRepository userRepository, UserProfileRepository userProfileRepository,
            UserLoginAuditLogService userLoginAuditLogService,
            UserSessionService userSessionService, UserSessionRepository userSessionRepository, UserMapper userMapper,
            RedisTemplate<String, String> redisTemplate,
            EmailQueueService emailQueueService,
            OrganizationRepository organizationRepository,
            UserVerificationService userVerificationService, UserVerificationRepository userVerificationRepository,
            OrganizationService organizationService) {
        this.otpService = otpService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenUtility = tokenUtility;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userLoginAuditLogService = userLoginAuditLogService;
        this.userSessionService = userSessionService;
        this.userSessionRepository = userSessionRepository;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
        this.emailQueueService = emailQueueService;
        this.organizationRepository = organizationRepository;
        this.userVerificationService = userVerificationService;
        this.userVerificationRepository = userVerificationRepository;
        this.organizationService = organizationService;
    }

    @Transactional
    public String registerUser(UserRegistrationRequest request) {
        validateUser(request);
        User user = userMapper.toRegisterUser(request);
        User savedUser = userRepository.save(user);
        UserProfile userProfile = userMapper.toRegisterUserProfile(savedUser.getId(), request);
        userProfileRepository.save(userProfile);
        userVerificationService.createUserVerification(user.getId());
        sendOtpForEmailVerification(request.getOrganizationId(), request.getEmail());
        return UserMessage.USER_REGISTER;
    }

    @Transactional
    public String sendOtpForEmailVerification(UUID organizationId, String email) {
        organizationService.findById(organizationId);
        User user = findByEmail(email);
        if (!user.getOrganizationId().equals(organizationId)) {
            throw new InvalidRequestException("User does not belong to the organization.");
        }
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        if (Boolean.TRUE.equals(userVerification.getIsEmailVerified())) {
            throw new InvalidRequestException("Email is already verified.");
        }
        String otp = otpService.generateOtp();
        userSessionService.createUserSession(user.getId(), otp);
        emailQueueService.addToQueue(user.getId(), user.getEmail(), otp);
        return "Your email verification OTP has been sent successfully. Please verify your email to continue..";
    }

    @Transactional
    public String verifyEmailOtp(VerifyOtpRequest request) {
        User user = findByEmail(request.getEmail());
        validateEmailVerification(user.getId());
        UserSession session = validateOtpSession(user.getId());
        validateOtp(session, request.getOtp(), user.getId());
        userSessionService.markOtpVerified(user.getId());
        userVerificationService.completeEmailVerification(user.getId());
        return "Email verified successfully.";
    }

    private void validateEmailVerification(UUID userId) {
        UserVerification verification = findByVerificationByUserId(userId);
        if (Boolean.TRUE.equals(verification.getIsEmailVerified())) {
            throw new InvalidRequestException("Email is already verified.");
        }
    }

    private UserSession validateOtpSession(UUID userId) {
        UserSession session = userSessionRepository
                .findTopByUserIdOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> new RecordNotFoundException("OTP session not found."));
        if (session.getOtp() == null) {
            throw new OtpNotFoundException("OTP not found.");
        }
        return session;
    }

    private void validateOtp(UserSession session, String otp, UUID userId) {
        if (session.getOtpVerificationCount() >= 3) {
            throw new MaxOtpAttemptException("Maximum OTP verification attempts reached.");
        }
        if (LocalDateTime.now().isAfter(session.getOtpExpiration())) {
            throw new OtpExpiredException("OTP has expired.");
        }
        if (!bCryptPasswordEncoder.matches(otp, session.getOtp())) {

            int attempts = userSessionService.incrementOtpVerificationCount(userId);
            if (attempts >= 3) {
                throw new MaxOtpAttemptException("Maximum OTP verification attempts reached.");
            }
            throw new InvalidOtpException("Invalid OTP.");
        }
    }

    private UserVerification findByVerificationByUserId(UUID id) {
        return userVerificationRepository.findByUserId(id)
                .orElseThrow(() -> new RecordNotFoundException("User id not found"));
    }

    @Transactional
    public User updateUser(UUID id, UserUpdationRequest request) {
        User user = findById(id);
        if ("DELETED".equalsIgnoreCase(user.getStatus())) {
            throw new InvalidRequestException("User is deleted and cannot be updated.");
        }
        User newuser = userMapper.toUpdateUser(user, request);
        User savedUser = userRepository.save(newuser);
        UserProfile userProfile = findUserProfileByUserId(id);
        UserProfile updatedUserProfile = userMapper.toUpdateUserProfile(userProfile, request);
        userProfileRepository.save(updatedUserProfile);
        return savedUser;
    }

    public void deleteUserById(UUID id) {
        User user = findById(id);
        user.setStatus(AppConstants.DELETED);
        userRepository.save(user);
        UserProfile userProfile = findUserProfileByUserId(id);
        userProfile.setStatus(AppConstants.DELETED);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public String loginUser(LoginRequest loginRequest) {
        User user = getUser(loginRequest.getLogin());
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        validateAccountStatus(userVerification);
        validatePassword(loginRequest.getPassword(), user, userVerification);
        resetFailedLoginAttempts(userVerification);
        String otp = otpService.generateOtp();
        userSessionService.createUserSession(user.getId(), otp);
        EmailQueue emailQueue = emailQueueService.addToQueue(user.getId(),
                user.getEmail(), otp);
        otpService.sendEmail(emailQueue);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(),
                user.getId(), "Login", "Success");
        return "Otp send successfully";
    }

    public ApiResponse verifyLoginOtp(VerifyOtpRequest verifyOtpRequest) {
        User user = findByEmail(verifyOtpRequest.getEmail());
        otpService.verifyOtp(user.getId(), verifyOtpRequest.getOtp());
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        if ("SUPERADMIN".equalsIgnoreCase(user.getUserType()) && !Boolean.TRUE.equals(
                userVerification.getIsPasswordResetCompleted())) {
            String resetToken = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("PASSWORD_RESET:" + resetToken, user.getId().toString(), 10,
                    TimeUnit.MINUTES);
            ApiResponse response = new ApiResponse(
                    AppConstants.SUCCESS,
                    "OTP verified successfully. Please change your temporary password to continue.",
                    200);
            response.setResetToken(resetToken);
            return response;
        }
        String accessToken = tokenUtility.generateJwt(user.getUserName());
        if (!Boolean.TRUE.equals(userVerification.getIsUserOnboarded())) {
            userVerification.setIsUserOnboarded(true);
            userVerificationRepository.save(userVerification);
        }
        String refreshKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(accessToken, user.getId().toString(), 10, TimeUnit.MINUTES);
        userSessionService.updateSessionAfterOtp(user.getId(), refreshKey);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Verify Otp", "Success");
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP_VERIFY, 201);
        response.setAccessToken(accessToken);
        response.setRefreshKey(refreshKey);
        return response;
    }

    @Transactional
    public void changeTemporaryPassword(ChangeTemporaryPasswordRequest request) {
        User user = getUserByResetToken(request.getResetToken());
        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {
            throw new BusinessException(
                    "New password and confirm password do not match");
        }
        user.setPassword(bCryptPasswordEncoder.encode(
                request.getNewPassword()));
        userRepository.save(user);
        UserVerification verification = findByVerificationByUserId(user.getId());
        verification.setIsPasswordResetCompleted(true);
        userVerificationRepository.save(verification);
        redisTemplate.delete(
                "PASSWORD_RESET:" + request.getResetToken());
    }

    private User getUserByResetToken(String resetToken) {
        String userId = (String) redisTemplate.opsForValue()
                .get("PASSWORD_RESET:" + resetToken);
        if (userId == null) {
            throw new BusinessException("Invalid or expired reset token");
        }
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
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
        redisTemplate.opsForValue().set(newToken, user.getId().toString(), 10, TimeUnit.MINUTES);
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
        redisTemplate.delete(accessToken);
        userSessionRepository.save(userSession);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "logout", "success");
        return "user logout successfully";
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
        validateEmail(request.getEmail());
        validateContactNumber(request.getContactNumber());
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
                .orElseThrow(() -> new RecordNotFoundException("User email not found."));
    }

    private void validateAccountStatus(UserVerification user) {
        if (!Boolean.TRUE.equals(user.getIsAccountLocked())) {
            return;
        }
        if (user.getLockedTime() != null &&
                LocalDateTime.now().isBefore(user.getLockedTime().plusMinutes(30))) {
            throw new AccountLockException("Account is locked");
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
                    user.getId(), "Login", "Account_Locked");
            throw new AccountLockException("Account locked due to 3 failed login attempts");
        }
        int remainingAttempts = 3 - attempts;
        userLoginAuditLogService.createUserLog(user.getOrganizationId(),
                user.getId(), "Login", "failed");
        throw new InvalidCredentialsException(
                "Invalid credentials. " + remainingAttempts + " attempt"
                        + (remainingAttempts > 1 ? "s" : "")
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

    public User findByOrganizationIdAndUserType(UUID organizationId, String userType) {
        return userRepository.findByOrganizationIdAndUserType(organizationId, "SUPERADMIN")
                .orElseThrow(() -> new RecordNotFoundException("Organization id and user type not found"));
    }

    @Transactional
    public void sendTemporaryCredentials(UUID userId) {
        User user = findById(userId);
        String temporaryPassword = Apputility.generateTemporaryPassword();
        user.setPassword(bCryptPasswordEncoder.encode(temporaryPassword));
        userRepository.save(user);
        emailQueueService.addTemporaryPasswordEmail(user.getId(), user.getEmail(),
                user.getUserName(),
                temporaryPassword);
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        userVerification.setIsPasswordResetCompleted(false);
        userVerificationRepository.save(userVerification);
    }

    @Transactional
    public void resendTemporaryCredentials(UUID userId) {
        User user = findById(userId);
        EmailQueue emailQueue = emailQueueService.findLatestEmailByUserId(userId);
        if (!"FAILED".equalsIgnoreCase(emailQueue.getStatus())) {
            throw new BusinessException("Temporary credentials email cannot be resent");
        }
        String temporaryPassword = Apputility.generateTemporaryPassword();
        user.setPassword(bCryptPasswordEncoder.encode(temporaryPassword));
        userRepository.save(user);
        emailQueueService.addTemporaryPasswordEmail(user.getId(), user.getEmail(), user.getUserName(),
                temporaryPassword);
        UserVerification userVerification = findByVerificationByUserId(user.getId());
        userVerification.setIsPasswordResetCompleted(false);
        userVerificationRepository.save(userVerification);
    }
}
