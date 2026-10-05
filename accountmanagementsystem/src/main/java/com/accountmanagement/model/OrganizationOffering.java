package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.enums.GatewayStatus;
import com.accountmanagement.model.listeners.OrganizationOfferingListener;
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
@Table(name = "organization_offerings")
@Data
@EntityListeners(OrganizationOfferingListener.class)
public class OrganizationOffering {

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

    @Column(name = "offering_type_id")
    private Integer offeringTypeId;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "payment_transaction_id")
    private UUID paymentTransactionId;

    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "bank_account_id")
    private UUID bankAccountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway_status")
    private GatewayStatus gatewayStatus;

    @Column(name = "received_date")
    private LocalDate receivedDate;

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
