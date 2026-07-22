package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Organization;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class OrganizationListener {

    @PrePersist
    public void onCreateOrganization(Organization organization) {
        organization.setStatus(AppConstants.ACTIVE);
        organization.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateOrganization(Organization organization) {
        organization.setUpdatedAt(LocalDateTime.now());
    }
}
