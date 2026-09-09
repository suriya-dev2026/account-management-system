package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.User;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserListeners {

    @PrePersist
    public void onCreate(User user) {
        user.setStatus(AppConstants.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(User user) {
        user.setUpdatedAt(LocalDateTime.now());
    }

}
