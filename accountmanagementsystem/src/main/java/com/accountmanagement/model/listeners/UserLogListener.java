package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.UserLog;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserLogListener {

    @PrePersist
    public void createUserLog(UserLog userLog) {
        userLog.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void updateUserLog(UserLog userLog) {
        userLog.setUpdatedAt(LocalDateTime.now());
    }
}
