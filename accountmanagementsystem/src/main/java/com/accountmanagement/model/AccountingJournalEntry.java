package com.accountmanagement.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.model.listeners.AccountingJournalEntryListener;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounting_journal_entries")
@Data
@EntityListeners(AccountingJournalEntryListener.class)
public class AccountingJournalEntry {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "tracking_id", unique = true, nullable = false)
    private String trackingId;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private UUID createdBy;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
