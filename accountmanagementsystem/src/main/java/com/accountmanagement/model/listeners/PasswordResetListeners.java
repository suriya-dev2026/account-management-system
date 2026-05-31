package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.PasswordReset;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class PasswordResetListeners {

    @PrePersist
    public void onCreate(PasswordReset passwordReset) {
        passwordReset.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(PasswordReset passwordReset) {
        passwordReset.setUpdatedAt(LocalDateTime.now());
    }

}
