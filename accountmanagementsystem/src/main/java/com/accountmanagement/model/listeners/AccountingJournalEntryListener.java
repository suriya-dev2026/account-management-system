package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.AccountingJournalEntry;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class AccountingJournalEntryListener {

    @PrePersist
    public void onCreateAccountingJournalEntry(AccountingJournalEntry accountingJournalEntry) {
        accountingJournalEntry.setStatus(AppConstants.POSTED);
        accountingJournalEntry.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateAccountingJournalEntry(AccountingJournalEntry accountingJournalEntry) {
        accountingJournalEntry.setUpdatedAt(LocalDateTime.now());
    }
}
