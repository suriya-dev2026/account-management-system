package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.UserSession;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserSessionListeners {

    @PrePersist
    public void onCreate(UserSession userSession) {
        userSession.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(UserSession userSession) {
        userSession.setUpdatedAt((LocalDateTime.now()));
    }
}
