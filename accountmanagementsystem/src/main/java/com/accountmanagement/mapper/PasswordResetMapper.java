package com.accountmanagement.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.PasswordReset;

@Component
public class PasswordResetMapper {

    public PasswordReset toPasswordReset(UUID id, String otp) {
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserId(id);
        passwordReset.setOtp(otp);
        passwordReset.setOtpExpiration(LocalDateTime.now().plusMinutes(2));
        passwordReset.setIsOtpVerified(false);
        passwordReset.setOtpVerificationCount(0);
        return passwordReset;
    }
}
