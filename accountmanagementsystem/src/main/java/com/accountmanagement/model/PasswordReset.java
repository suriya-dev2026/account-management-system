package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "password_resets")
@EntityListeners(PasswordResetListeners.class)
public class PasswordReset {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiration")
    private LocalDateTime otpExpiration;

    @Column(name = "otp_verification_count")
    private Integer otpVerificationCount;

    @Column(name = "is_otp_verified")
    private Boolean isOtpVerified;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
