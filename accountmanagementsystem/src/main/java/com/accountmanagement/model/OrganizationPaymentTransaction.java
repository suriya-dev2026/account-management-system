package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.accountmanagement.enums.Gateway;
import com.accountmanagement.enums.GatewayStatus;
import com.accountmanagement.model.listeners.OrganizationPaymentTransactionListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "organization_payment_transactions")
@Data
@EntityListeners(OrganizationPaymentTransactionListener.class)
public class OrganizationPaymentTransaction {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gateway")
    private Gateway gateway;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "gateway_reference")
    private String gatewayReference;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response_json", columnDefinition = "jsonb")
    private JsonNode responseJson;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(name = "verified")
    private Boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GatewayStatus status;

    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
