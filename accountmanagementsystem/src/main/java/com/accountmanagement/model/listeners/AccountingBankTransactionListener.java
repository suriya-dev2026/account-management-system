package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingBankTransaction;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccountingBankTransactionListener {

    @PrePersist
    public void onCreateAccountingBankTransaction(AccountingBankTransaction accountingBankTransaction) {
        accountingBankTransaction.setStatus(AppConstants.ACTIVE);
        accountingBankTransaction.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingBankTransaction(AccountingBankTransaction accountingBankTransaction) {
        accountingBankTransaction.setUpdatedAt(LocalDateTime.now());
    }
}
