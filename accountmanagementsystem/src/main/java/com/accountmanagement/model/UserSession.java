package com.accountmanagement.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.UserSessionListeners;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users_sessions")
@EntityListeners(UserSessionListeners.class)
public class UserSession {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiration")
    private LocalDateTime otpExpiration;

    @Column(name = "otp_verification_count")
    private Integer otpVerificationCount;

    @Column(name = "is_otp_verified")
    private Boolean isOtpVerified;

    @Column(name = "refresh_key")
    private String refreshKey;

    @Column(name = "refresh_key_created_at")
    private LocalDateTime refreshKeyCreatedAt;

    @Column(name = "refresh_key_expiration")
    private LocalDateTime refreshKeyExpiration;

    @Column(name = "refresh_key_status")
    private Boolean refreshKeyStatus;

    @Column(name = "session_status")
    private String sessionStatus;

    @Column(name = "is_valid_token")
    private Boolean isValidToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
