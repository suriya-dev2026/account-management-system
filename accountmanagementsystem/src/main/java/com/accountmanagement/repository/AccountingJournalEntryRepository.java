package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.accountmanagement.model.AccountingJournalEntry;

public interface AccountingJournalEntryRepository extends JpaRepository<AccountingJournalEntry, UUID> {

    @Query(value = "SELECT nextval('accounting_journal_tracking_seq')", nativeQuery = true)
    Long getNextJournalTrackingSequence();
}
