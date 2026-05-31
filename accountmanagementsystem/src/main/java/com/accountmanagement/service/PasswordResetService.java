package com.accountmanagement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.PasswordReset;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.PasswordResetRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.VerifyOtpRequest;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RecordNotFoundException("email not found"));
        String otp = otpService.generateOtp();
        otpService.sendOtp(email, otp);
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

    public PasswordReset verifyResetOtp(VerifyOtpRequest verifyOtpRequest) {
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
        userLogService.createUserLog(user.getId(), "verify otp", "success");

        return passwordResetRepository.save(passwordReset);
    }

    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new RecordNotFoundException("Invalid email"));
        PasswordReset passwordReset = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() -> new RecordNotFoundException("user session not found"));
        if (!passwordReset.getIsOtpVerified()) {
            throw new RuntimeException("Otp not verified");
        }
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getChangedPassword()));
        userRepository.save(user);
        passwordReset.setResetOtp(null);
        passwordReset.setIsOtpVerified(false);
        passwordReset.setOtpVerificationCount(0);
        passwordResetRepository.save(passwordReset);
        return "password changed successfully";

    }
}
