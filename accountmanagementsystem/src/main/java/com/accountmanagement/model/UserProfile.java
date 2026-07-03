package com.accountmanagement.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.UserProfileListeners;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_profiles")
@Data
@EntityListeners(UserProfileListeners.class)
public class UserProfile {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "address")
    private String address  ;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "is_account_locked")
    private Boolean isAccountLocked;

    @Column(name = "locked_time")
    private LocalDateTime lockedTime;

    @Column
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
