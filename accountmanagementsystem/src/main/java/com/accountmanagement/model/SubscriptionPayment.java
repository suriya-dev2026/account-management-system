package com.accountmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.accountmanagement.enums.PaymentStatus;
import com.accountmanagement.enums.PaymentType;
import com.accountmanagement.model.listeners.SubscriptionPaymentListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "subscription_payments")
@Data
@EntityListeners(SubscriptionPaymentListener.class)
public class SubscriptionPayment {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "subscription_organization_id")
    private UUID subscriptionOrganizationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "discount_percentage")
    private BigDecimal discountPercentage;

    @Column(name = "billing_amount")
    private BigDecimal billingAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "payment_provider")
    private String paymentProvider;

    @Column(name = "payment_reference")
    private String paymentReference;

    @Column(name = "transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
