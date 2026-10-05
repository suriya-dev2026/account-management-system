package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationPaymentMethod;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class OrganizationPaymentMethodListener {

    @PrePersist
    public void onCreateOrganizationPaymentMethod(OrganizationPaymentMethod organizationPaymentMethod) {
        organizationPaymentMethod.setStatus(AppConstants.ACTIVE);
        organizationPaymentMethod.setCreatedAt(LocalDateTime.now());
    }
}
