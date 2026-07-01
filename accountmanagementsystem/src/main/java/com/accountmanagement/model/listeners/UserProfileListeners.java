package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.UserProfile;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class UserProfileListeners {

    @PrePersist
    public void onCreate(UserProfile userProfile) {
        userProfile.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(UserProfile userProfile) {
        userProfile.setUpdatedAt(LocalDateTime.now());
    }
}
