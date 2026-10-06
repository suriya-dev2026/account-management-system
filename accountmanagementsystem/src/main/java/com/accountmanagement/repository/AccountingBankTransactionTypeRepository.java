package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.AccountingBankTransactionType;

public interface AccountingBankTransactionTypeRepository extends JpaRepository<AccountingBankTransactionType, Integer> {

    boolean existsByOrganizationIdAndTypeName(UUID organizationId, String typeName);

    Optional<AccountingBankTransactionType> findByIdAndOrganizationId(Integer transactionTypeId, UUID organizationId);

}
