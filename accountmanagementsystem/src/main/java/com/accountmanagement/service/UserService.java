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
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.exceptions.AccountLockException;
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
import com.accountmanagement.mapper.PasswordResetMapper;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.EmailQueue;
import com.accountmanagement.model.PasswordReset;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.PasswordResetRepository;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@Service
public class UserService {

    private final OtpService otpService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenUtility tokenUtility;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final UserSessionService userSessionService;

    private final UserSessionRepository userSessionRepository;

    private final UserMapper userMapper;

    private final PasswordResetRepository passwordResetRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private final EmailQueueService emailQueueService;

    private final PasswordResetMapper passwordResetMapper;

    private final UserSecurityService userSecurityService;

    UserService(OtpService otpService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenUtility tokenUtility,
            UserRepository userRepository, UserProfileRepository userProfileRepository,
            UserLoginAuditLogService userLoginAuditLogService,
            UserSessionService userSessionService, UserSessionRepository userSessionRepository, UserMapper userMapper,
            PasswordResetRepository passwordResetRepository, RedisTemplate<String, String> redisTemplate,
            EmailQueueService emailQueueService, PasswordResetMapper passwordResetMapper,
            UserSecurityService userSecurityService) {
        this.otpService = otpService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenUtility = tokenUtility;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userLoginAuditLogService = userLoginAuditLogService;
        this.userSessionService = userSessionService;
        this.userSessionRepository = userSessionRepository;
        this.userMapper = userMapper;
        this.passwordResetRepository = passwordResetRepository;
        this.redisTemplate = redisTemplate;
        this.emailQueueService = emailQueueService;
        this.passwordResetMapper = passwordResetMapper;
        this.userSecurityService = userSecurityService;
    }

    @Transactional
    public User registerUser(UserRegistrationRequest userRequest) {
        validateUser(userRequest);
        User user = userMapper.toRegisterUser(userRequest);
        User savedUser = userRepository.save(user);
        UserProfile userProfile = userMapper.toUserProfile(userRequest, savedUser.getId());
        userProfileRepository.save(userProfile);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "register", "success");
        return savedUser;
    }

    @Transactional
    public String loginUser(LoginRequest loginRequest) {
        User user = getUser(loginRequest.getLogin());
        validateAccountStatus(user);
        validatePassword(loginRequest.getPassword(), user);
        resetFailedLoginAttempts(user);
        String otp = otpService.generateOtp();
        userSessionService.createUserSession(user.getId(), otp);
        EmailQueue emailQueue = emailQueueService.addToQueue(user.getId(), user.getEmail());
        otpService.sendEmail(emailQueue, otp);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Login", "Success");
        return "Otp send successfully";
    }

    public ApiResponse verifyLoginOtp(VerifyOtpRequest verifyOtpRequest) {
        User user = findByEmail(verifyOtpRequest.getEmail());
        otpService.verifyOtp(user.getId(), verifyOtpRequest.getOtp());
        String accessToken = tokenUtility.generateJwt(user.getUserName());
        String refreshKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(accessToken, user.getId().toString(), 10, TimeUnit.MINUTES);
        userSessionService.updateSessionAfterOtp(user.getId(), refreshKey);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Verify Otp", "Success");
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP_VERIFY, 201);
        response.setAccessToken(accessToken);
        response.setRefreshKey(refreshKey);
        return response;
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

    public List<UserDto> getAllUsers() {
        User loggedUser = Apputility.getLoggedUser();
        userLoginAuditLogService.createUserLog(loggedUser.getOrganizationId(), loggedUser.getId(), "Get All Users",
                "success");
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getContactNumber());
            return userDto;
        }).toList();
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

    @Transactional
    public String forgotPassword(String email) {
        User user = findByEmail(email);
        String otp = otpService.generateOtp();
        EmailQueue emailQueue = emailQueueService.addToQueue(user.getId(), email);
        otpService.sendEmail(emailQueue, otp);
        String hashedOtp = bCryptPasswordEncoder.encode(otp);
        PasswordReset passwordReset = passwordResetMapper.toPasswordReset(user.getId(), hashedOtp);
        passwordResetRepository.save(passwordReset);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "password reset", "success");
        return "otp sent successfully";
    }

    public ApiResponse verifyResetOtp(VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        User user = findByEmail(verifyOtpRequest.getEmail());
        PasswordReset passwordReset = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new RecordNotFoundException("password reset request not found"));
        validateOtp(passwordReset, verifyOtpRequest.getOtp());
        String resetToken = markOtpVerified(passwordReset);
        passwordResetRepository.save(passwordReset);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "verify otp", "success");
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP_VERIFY, 200);
        response.setRefreshKey(resetToken);
        return response;
    }

    @Transactional
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        validateChangePassword(changePasswordRequest);
        PasswordReset passwordReset = getValidResetToken(changePasswordRequest.getResetToken());
        User user = userRepository.findById(passwordReset.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not Found"));
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        clearPasswordReset(passwordReset);
        return "password changed successfully";
    }

    private void validateUser(UserRegistrationRequest userRequest) {
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        if (userRepository.existsByContactNumber(userRequest.getContactNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match");
        }
    }

    private User getUser(String login) {
        return userRepository.findByUserNameOrEmailOrContactNumber(login, login, login)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
    }

    private void validateAccountStatus(User user) {
        if (!Boolean.TRUE.equals(user.getIsAccountLocked())) {
            return;
        }
        if (user.getLockedTime() != null && LocalDateTime.now().isBefore(user.getLockedTime().plusMinutes(30))) {
            throw new AccountLockException("Account is locked");
        }
        user.setIsAccountLocked(false);
        user.setFailedLoginAttempts(0);
        user.setLockedTime(null);
        userRepository.save(user);
    }

    private void validatePassword(String password, User user) {
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return;
        }
        int attempts = user.getFailedLoginAttempts() + 1;
        user.setFailedLoginAttempts(attempts);
        if (attempts >= 3) {
            user.setIsAccountLocked(true);
            user.setLockedTime(LocalDateTime.now());
            userRepository.save(user);
            userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Login", "Account_Locked");
            throw new AccountLockException("Account locked due to 3 failed login attempts");
        }
        userSecurityService.saveUser(user);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "Login", "failed");
        throw new InvalidCredentialsException("Invalid Credentials");
    }

    private void resetFailedLoginAttempts(User user) {
        if (user.getFailedLoginAttempts() == 0 && !Boolean.TRUE.equals(user.getIsAccountLocked())) {
            return;
        }
        user.setFailedLoginAttempts(0);
        user.setIsAccountLocked(false);
        user.setLockedTime(null);
        userRepository.save(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("user email not found"));
    }

    private String markOtpVerified(PasswordReset passwordReset) {
        passwordReset.setOtp(null);
        passwordReset.setOtpVerificationCount(0);
        passwordReset.setIsOtpVerified(true);
        String resetToken = UUID.randomUUID().toString();
        passwordReset.setResetToken(resetToken);
        passwordReset.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
        return resetToken;
    }

    private void validateChangePassword(ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }
    }

    private PasswordReset getValidResetToken(String resetToken) {
        PasswordReset passwordReset = passwordResetRepository.findByResetToken(resetToken)
                .orElseThrow(() -> new RecordNotFoundException("reset token not found"));
        if (!Boolean.TRUE.equals(passwordReset.getIsOtpVerified())) {
            throw new InvalidRequestException("Otp verification required");
        }
        if (passwordReset.getResetTokenExpiry() == null
                || LocalDateTime.now().isAfter(passwordReset.getResetTokenExpiry())) {
            throw new InvalidRequestException("Reset token expired");
        }
        return passwordReset;
    }

    private void clearPasswordReset(PasswordReset passwordReset) {
        passwordReset.setOtp(null);
        passwordReset.setResetToken(null);
        passwordReset.setIsOtpVerified(false);
        passwordReset.setOtpVerificationCount(0);
        passwordResetRepository.save(passwordReset);

    }

    private void validateOtp(PasswordReset passwordReset, String enteredOtp) {
        if (passwordReset.getOtp() == null) {
            throw new OtpNotFoundException("Otp not found");
        }

        if (passwordReset.getOtpExpiration() == null || LocalDateTime.now().isAfter(passwordReset.getOtpExpiration())) {
            throw new OtpExpiredException("otp expired");
        }
        if (passwordReset.getOtpVerificationCount() >= 3) {
            throw new MaxOtpAttemptException("Maximum attempts reached");
        }
        if (!bCryptPasswordEncoder.matches(enteredOtp, passwordReset.getOtp())) {
            int attempts = passwordReset.getOtpVerificationCount() + 1;
            passwordReset.setOtpVerificationCount(attempts);
            passwordResetRepository.save(passwordReset);
            throw new InvalidOtpException("Invalid otp");
        }
    }

}
