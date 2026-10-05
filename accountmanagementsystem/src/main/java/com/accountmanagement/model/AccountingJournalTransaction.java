package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.enums.AccountingJournalTransactionStatus;
import com.accountmanagement.model.listeners.AccountingJournalTransactionListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounting_journal_transactions")
@Data
@EntityListeners(AccountingJournalTransactionListener.class)
public class AccountingJournalTransaction {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "journal_entry_id")
    private UUID journalEntryId;

    @Column(name = "journal_entry_tracking_id")
    private String journalEntryTrackingId;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "debit")
    private BigDecimal debit;

    @Column(name = "credit")
    private BigDecimal credit;

    @Column(name = "remarks")
    private String remarks;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountingJournalTransactionStatus status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
