package com.accountmanagement.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.model.listeners.UserVerificationListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_verifications")
@Data
@EntityListeners(UserVerificationListener.class)
public class UserVerification {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "is_user_onboarded")
    private Boolean isUserOnboarded;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_subscription_completed")
    private Boolean isSubscriptionCompleted;

    @Column(name = "is_password_reset_completed")
    private Boolean isPasswordResetCompleted;

    @Column(name = "profile_completed_percentage")
    private Integer profileCompletedPercentage;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_account_locked")
    private Boolean isAccountLocked;

    @Column(name = "locked_time")
    private LocalDateTime lockedTime;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
