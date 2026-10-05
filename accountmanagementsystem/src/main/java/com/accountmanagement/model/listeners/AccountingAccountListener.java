package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingAccount;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccountingAccountListener {

    @PrePersist
    public void onCreateAccountingAccount(AccountingAccount accountingAccount) {
        accountingAccount.setStatus(AppConstants.ACTIVE);
        accountingAccount.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingAccount(AccountingAccount accountingAccount) {
        accountingAccount.setUpdatedAt(LocalDateTime.now());
    }
}
