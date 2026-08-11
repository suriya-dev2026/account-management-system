package com.accountmanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.enums.PaymentStatus;
import com.accountmanagement.model.SubscriptionPayment;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, UUID> {

    boolean existsByTransactionId(String transactionId);

    Optional<SubscriptionPayment> findBySubscriptionOrganizationIdAndStatus(UUID subscriptionOrganizationId,
            PaymentStatus status);

    List<SubscriptionPayment> findBySubscriptionOrganizationId(UUID subscriptionOrganizationId);

    Optional<SubscriptionPayment> findByPaymentReference(String paymentReference);

    boolean existsBySubscriptionOrganizationIdAndStatus(UUID subscriptionOrganizationId, PaymentStatus status);

}
