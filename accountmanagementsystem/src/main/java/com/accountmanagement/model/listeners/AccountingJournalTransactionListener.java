package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.enums.AccountingJournalTransactionStatus;
import com.accountmanagement.model.AccountingJournalTransaction;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccountingJournalTransactionListener {

    @PrePersist
    public void onCreateAccountingJournalTransaction(AccountingJournalTransaction accountingJournalTransaction) {
        accountingJournalTransaction.setStatus(AccountingJournalTransactionStatus.ACTIVE);
        accountingJournalTransaction.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingJournalTransaction(AccountingJournalTransaction accountingJournalTransaction) {
        accountingJournalTransaction.setUpdatedAt(LocalDateTime.now());
    }
}
