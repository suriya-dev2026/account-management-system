package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationOfferingType;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class OrganizationOfferingTypeListener {

    @PrePersist
    public void onCreateAccountingOfferingType(OrganizationOfferingType organizationOfferingType) {
        organizationOfferingType.setStatus(AppConstants.ACTIVE);
        organizationOfferingType.setCreatedAt(LocalDateTime.now());
    }

}
