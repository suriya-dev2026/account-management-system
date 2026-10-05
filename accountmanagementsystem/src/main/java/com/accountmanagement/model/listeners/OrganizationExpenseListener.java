package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationExpense;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class OrganizationExpenseListener {

    @PrePersist
    public void onCreateExpense(OrganizationExpense organizationExpense) {
        organizationExpense.setStatus(AppConstants.POSTED);
        organizationExpense.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateExpense(OrganizationExpense organizationExpense) {
        organizationExpense.setUpdatedAt(LocalDateTime.now());
    }

}
