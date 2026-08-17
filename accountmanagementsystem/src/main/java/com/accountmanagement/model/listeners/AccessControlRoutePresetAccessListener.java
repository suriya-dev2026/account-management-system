package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlRoutePresetAccess;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccessControlRoutePresetAccessListener {

    @PrePersist
    public void onCreate(AccessControlRoutePresetAccess accessControlRoutePresetAccess) {
        accessControlRoutePresetAccess.setStatus(AppConstants.ACTIVE);
        accessControlRoutePresetAccess.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(AccessControlRoutePresetAccess accessControlRoutePresetAccess) {
        accessControlRoutePresetAccess.setUpdatedAt(LocalDateTime.now());
    }

}
