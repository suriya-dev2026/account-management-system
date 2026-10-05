package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.OrganizationPaymentTransaction;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class OrganizationPaymentTransactionListener {

    @PrePersist
    public void onCreateOrganizationPaymentTransaction(OrganizationPaymentTransaction organizationPaymentTransaction) {
        organizationPaymentTransaction.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateOrganizationPaymentTransaction(OrganizationPaymentTransaction organizationPaymentTransaction) {
        organizationPaymentTransaction.setUpdatedAt(LocalDateTime.now());
    }

}
