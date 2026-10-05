package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingBankAccount;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccountingBankAccountListener {

    @PrePersist
    public void onCreateAccountingBankAccount(AccountingBankAccount accountingBankAccount) {
        accountingBankAccount.setStatus(AppConstants.ACTIVE);
        accountingBankAccount.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingBankAccount(AccountingBankAccount accountingBankAccount) {
        accountingBankAccount.setUpdatedAt(LocalDateTime.now());
    }
}
