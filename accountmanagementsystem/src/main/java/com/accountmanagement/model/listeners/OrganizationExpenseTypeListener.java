package com.accountmanagement.model.listeners;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.OrganizationExpenseType;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class OrganizationExpenseTypeListener {

    @PrePersist
    public void onCreateExpenseType(OrganizationExpenseType expenseType) {
        expenseType.setStatus(AppConstants.ACTIVE);
    }
}
