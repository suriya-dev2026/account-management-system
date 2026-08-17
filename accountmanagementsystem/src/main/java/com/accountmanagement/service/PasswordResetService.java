package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.exceptions.InvalidOtpException;
import com.accountmanagement.exceptions.InvalidRequestException;
import com.accountmanagement.exceptions.MaxOtpAttemptException;
import com.accountmanagement.exceptions.OtpExpiredException;
import com.accountmanagement.exceptions.OtpNotFoundException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.PasswordResetMapper;
import com.accountmanagement.model.PasswordReset;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.PasswordResetRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final PasswordResetRepository passwordResetRepository;

    private final UserService userService;

    private final OtpService otpService;

    private final EmailQueueService emailQueueService;

    private final UserLoginAuditLogService userLoginAuditLogService;

    private final PasswordResetMapper passwordResetMapper;

    public PasswordResetService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            PasswordResetMapper passwordResetMapper,
            PasswordResetRepository passwordResetRepository, UserService userService, OtpService otpService,
            EmailQueueService emailQueueService, UserLoginAuditLogService userLoginAuditLogService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordResetRepository = passwordResetRepository;
        this.userService = userService;
        this.otpService = otpService;
        this.emailQueueService = emailQueueService;
        this.userLoginAuditLogService = userLoginAuditLogService;
        this.passwordResetMapper = passwordResetMapper;
    }

    @Transactional
    public String forgotPassword(String email) {
        User user = userService.findByEmail(email);
        String otp = otpService.generateOtp();
        emailQueueService.addToPasswordResetQueue(user.getId(), email, otp);
        String hashedOtp = bCryptPasswordEncoder.encode(otp);
        PasswordReset passwordReset = passwordResetMapper.toPasswordReset(user.getId(), hashedOtp);
        passwordResetRepository.save(passwordReset);
        userLoginAuditLogService.createUserLog(user.getOrganizationId(), user.getId(), "password reset", "success");
        return "otp sent successfully";
    }

    @Transactional
    public ApiResponse verifyResetOtp(VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        User user = userService.findByEmail(verifyOtpRequest.getEmail());
        PasswordReset passwordReset = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new RecordNotFoundException("password reset request not found"));
        validateOtp(passwordReset, verifyOtpRequest.getOtp());
        String resetToken = markOtpVerified(passwordReset);
        passwordResetRepository.save(passwordReset);
        System.out.println(passwordReset.getIsOtpVerified());
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

    private String markOtpVerified(PasswordReset passwordReset) {
        passwordReset.setOtp(null);
        passwordReset.setOtpVerificationCount(0);
        passwordReset.setIsOtpVerified(true);
        String resetToken = UUID.randomUUID().toString();
        passwordReset.setResetToken(resetToken);
        passwordReset.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
        return resetToken;
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
