package com.accountmanagement.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.PasswordResetListeners;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "password_reset")
@EntityListeners(PasswordResetListeners.class)
public class PasswordReset {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reset_otp")
    private String resetOtp;

    @Column(name = "otp_expiration")
    private LocalDateTime otpExpiration;

    @Column(name = "is_otp_verified")
    private Boolean isOtpVerified;

    @Column(name = "otp_verification_count")
    private Integer otpVerificationCount;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
