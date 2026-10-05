package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationOffering;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class OrganizationOfferingListener {

    @PrePersist
    public void onCreateAccountingOffering(OrganizationOffering organizationOffering) {
        organizationOffering.setStatus(AppConstants.POSTED);
        organizationOffering.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingOffering(OrganizationOffering organizationOffering) {
        organizationOffering.setUpdatedAt(LocalDateTime.now());
    }
}
