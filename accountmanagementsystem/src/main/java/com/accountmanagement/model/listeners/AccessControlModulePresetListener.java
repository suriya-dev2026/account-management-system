package com.accountmanagement.model.listeners;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlModulePreset;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class AccessControlModulePresetListener {

    @PrePersist
    public void onCreateaAccessControlModulePreset(AccessControlModulePreset accessControlModulePreset) {
        accessControlModulePreset.setStatus(AppConstants.ACTIVE);
    }
}
