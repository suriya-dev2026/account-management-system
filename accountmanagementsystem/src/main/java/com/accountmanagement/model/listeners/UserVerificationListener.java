package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.UserVerification;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class UserVerificationListener {

    @PrePersist
    public void onCreateUserVerification(UserVerification userVerification) {
        userVerification.setStatus(AppConstants.ACTIVE);
        userVerification.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateUserVerification(UserVerification userVerification) {
        userVerification.setUpdatedAt(LocalDateTime.now());
    }
}
