package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccessControlUserRole;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccessControlUserRoleListener {

    @PrePersist
    public void onCreateAccessControlUserRole(AccessControlUserRole accessControlUserRole) {
        accessControlUserRole.setStatus(AppConstants.ACTIVE);
        accessControlUserRole.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccessControlUserRole(AccessControlUserRole accessControlUserRole) {
        accessControlUserRole.setUpdatedAt(LocalDateTime.now());
    }

}
