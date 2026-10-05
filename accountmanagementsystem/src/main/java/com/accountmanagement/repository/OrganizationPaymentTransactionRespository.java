package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationPaymentTransaction;

public interface OrganizationPaymentTransactionRespository extends JpaRepository<OrganizationPaymentTransaction, UUID> {

    Optional<OrganizationPaymentTransaction> findByTransactionId(String transactionId);

}
