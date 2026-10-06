package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounting_bank_transactions")
@Data
public class AccountingBankTransaction {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id")
    private UUID organizationId;

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Column(name = "journal_entry_id")
    private UUID journalEntryId;

    @Column(name = "journal_entry_tracking_id")
    private String journalEntryTrackingId;

    @Column(name = "transaction_type_id")
    private Integer transactionTypeId;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
