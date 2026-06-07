package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.UserMapper;
import com.accountmanagement.model.PasswordReset;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserSession;
import com.accountmanagement.repository.PasswordResetRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.repository.UserSessionRepository;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.utility.TokenUtility;

@Service
public class UserService {

    @Autowired
    private OtpService otpService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailQueueService emailQueueService;

    public User registerUser(UserRegistrationRequest userRequest) {
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        if (userRepository.existsByPhone(userRequest.getPhone())) {
            throw new UserAlreadyExistsException("Phone already registered");
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        User user = userMapper.toEntity(userRequest);
        User registeredUser = userRepository.save(user);
        userLogService.createUserLog(registeredUser.getId(), "register", "success");
        return registeredUser;
    }

    public String loginUser(LoginRequest loginRequest) {
        User user = userRepository
                .findByLoginUser(loginRequest.getUserName())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid user credentials"));

        if (user.getIsAccountLocked()) {
            if (LocalDateTime.now().isBefore(user.getLockedTime().plusMinutes(30))) {
                throw new InvalidCredentialsException("Account locked.Try again after 30 minutes");
            }
            user.setIsAccountLocked(false);
            user.setFailedLoginAttempts(0);
            user.setLockedTime(null);
            user.setStatus(AppConstants.ACTIVE);
            userRepository.save(user);
        }
        boolean isPasswordValid = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordValid) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 3) {
                user.setIsAccountLocked(true);
                user.setLockedTime(LocalDateTime.now());
                user.setStatus("locked");
            }
            userRepository.save(user);
            throw new InvalidCredentialsException("Invalid Password");
        }
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
        String otp = otpService.generateOtp();
        emailQueueService.addToQueue(user.getId(), user.getEmail(), otp);

        userSessionService.createUserSession(user.getId(), otp);
        userLogService.createUserLog(user.getId(), "login", "success");
        return "Otp send successfully";
    }

    public Map<String, String> verifyLoginOtp(VerifyOtpRequest verifyOtpRequest) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(verifyOtpRequest.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("Wrong email"));
        otpService.verifyOtp(verifyOtpRequest.getEmail(), verifyOtpRequest.getOtp());
        String accessToken = tokenUtility.generateJwt(user.getUserName());
        String refreshKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(accessToken, user.getId(), 10, TimeUnit.MINUTES);
        response.put("accessToken", accessToken);
        response.put("refreshKey", refreshKey);
        userSessionService.updateSessionAfterOtp(user.getId(), refreshKey, accessToken);
        userLogService.createUserLog(user.getId(), "Verify Otp", "success");
        return response;
    }

    public String generateAccessToken(String refreshKey) {
        UserSession userSession = userSessionService.getRefreshKey(refreshKey);
        if (userSession == null) {
            throw new RecordNotFoundException("Refresh Key not found");
        }
        if (!userSession.getRefreshKeyStatus()) {
            throw new RuntimeException("Refresh key invalid");
        }
        if (userSession.getRefreshKeyExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh key expired.Please login again");
        }
        if (userSession.getSessionStatus().equalsIgnoreCase("logout")) {
            throw new RuntimeException("Please login again");
        }
        String newToken = tokenUtility.generateJwt(userSession.getUserId());
        return newToken;
    }

    public List<UserDto> getAllUsers() {
        User loggedUser = Apputility.getLoggedUser();
        userLogService.createUserLog(loggedUser.getId(), "Get All Users", "success");
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setUserName(user.getUserName());
            userDto.setEmail(user.getEmail());
            return userDto;
        }).toList();
    }

    public String signout(String authHeader) {
        User user = Apputility.getLoggedUser();
        User newUser = userRepository.findByUserName(user.getUserName());
        if (newUser == null) {
            throw new RecordNotFoundException("User not found");
        }
        String accessToken = authHeader.replace("Bearer", "").trim();
        UserSession userSession = userSessionRepository.findTopByUserIdOrderByCreatedAtDesc(newUser.getId());
        if (userSession == null) {
            throw new RecordNotFoundException("User not found");
        }
        userSession.setSessionStatus("logout");
        userSession.setRefreshKey(null);
        userSession.setRefreshKeyStatus(false);
        userSession.setIsValidToken(false);
        redisTemplate.delete(accessToken);
        userSessionRepository.save(userSession);
        userLogService.createUserLog(user.getId(), "logout", "success");
        return "user logout successfully";
    }

    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("email not found"));
        String otp = otpService.generateOtp();
        emailQueueService.addToQueue(user.getId(), email, otp);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserId(user.getId());
        passwordReset.setResetOtp(otp);
        passwordReset.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
        passwordReset.setIsOtpVerified(false);
        passwordReset.setOtpVerificationCount(0);
        passwordResetRepository.save(passwordReset);
        userLogService.createUserLog(user.getId(), "password reset", "success");
        return "otp sent successfully";
    }

    public Map<String, String> verifyResetOtp(VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        Map<String, String> response = new HashMap<>();
        User user = userRepository.findByEmail(verifyOtpRequest.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("email not found"));

        PasswordReset passwordReset = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new RecordNotFoundException("session not found"));

        if (passwordReset.getResetOtp() == null) {
            throw new RuntimeException("otp not found");
        }
        if (passwordReset.getOtpExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Otp Expired");
        }
        if (passwordReset.getOtpVerificationCount() >= 3) {
            throw new RuntimeException("Maximum attempts reached");
        }
        if (!passwordReset.getResetOtp().equals(verifyOtpRequest.getOtp())) {
            passwordReset.setOtpVerificationCount(passwordReset.getOtpVerificationCount() + 1);
            passwordResetRepository.save(passwordReset);
            throw new RuntimeException("Invalid otp");
        }
        passwordReset.setOtpVerificationCount(0);
        passwordReset.setIsOtpVerified(true);
        String resetToken = UUID.randomUUID().toString();
        passwordReset.setResetToken(resetToken);
        passwordReset.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        response.put("resetToken", resetToken);
        userLogService.createUserLog(user.getId(), "verify otp", "success");
        return response;
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }
        PasswordReset passwordReset = passwordResetRepository.findByResetToken(changePasswordRequest.getResetToken())
                .orElseThrow(() -> new RecordNotFoundException("reset token not found"));
        if (passwordReset.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset Token Expired");
        }
        User user = userRepository.findById(passwordReset.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not Found"));
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        passwordReset.setResetOtp(null);
        passwordReset.setIsOtpVerified(false);
        passwordReset.setOtpVerificationCount(0);
        passwordReset.setResetToken(null);
        passwordReset.setTokenExpiry(null);
        passwordResetRepository.save(passwordReset);
        return "password changed successfully";
    }

}
