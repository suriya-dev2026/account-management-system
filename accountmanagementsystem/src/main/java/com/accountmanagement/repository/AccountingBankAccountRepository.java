package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccountingBankAccount;

public interface AccountingBankAccountRepository extends JpaRepository<AccountingBankAccount, UUID> {

    boolean existsByOrganizationIdAndAccountNumber(UUID organizationId, String accountNumber);

    Optional<AccountingBankAccount> findByIdAndOrganizationId(UUID bankAccountId, UUID organizationId);

}
