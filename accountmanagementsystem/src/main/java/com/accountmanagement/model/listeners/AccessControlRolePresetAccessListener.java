package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlRolePresetAccess;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccessControlRolePresetAccessListener {

    @PrePersist
    public void onCreateAccessControlRolePresetAccess(AccessControlRolePresetAccess accessControlRolePresetAccess) {
        accessControlRolePresetAccess.setStatus(AppConstants.ACTIVE);
        accessControlRolePresetAccess.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccessControlRolePresetAccess(AccessControlRolePresetAccess accessControlRolePresetAccess) {
        accessControlRolePresetAccess.setUpdatedAt(LocalDateTime.now());
    }
}
