package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlRole;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccessControlRoleListener {

    @PrePersist
    public void onCreate(AccessControlRole accessControlRole) {
        accessControlRole.setCreatedAt(LocalDateTime.now());
        accessControlRole.setStatus(AppConstants.ACTIVE);
    }

    @PreUpdate
    public void onUpdate(AccessControlRole accessControlRole) {
        accessControlRole.setUpdatedAt(LocalDateTime.now());
    }
}
