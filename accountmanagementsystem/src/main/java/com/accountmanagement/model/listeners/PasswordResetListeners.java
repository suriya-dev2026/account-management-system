package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.PasswordReset;

import jakarta.persistence.PrePersist;

public class PasswordResetListeners {

    @PrePersist
    public void onCreate(PasswordReset passwordReset) {
        passwordReset.setCreatedAt(LocalDateTime.now());
    }

}
