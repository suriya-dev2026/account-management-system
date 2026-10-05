package com.accountmanagement.model.listeners;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingBankTransactionType;

import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
public class AccountingBankTransactionTypeListener {

    @PrePersist
    public void onCreate(AccountingBankTransactionType accountingBankTransactionType) {
        accountingBankTransactionType.setStatus(AppConstants.ACTIVE);
    }
}
