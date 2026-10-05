package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccountingBankAccountType;

public interface AccountingBankAccountTypeRepository extends JpaRepository<AccountingBankAccountType, Integer> {

    boolean existsByOrganizationIdAndTypeName(UUID organizationId, String typeName);

    boolean existsByIdAndOrganizationId(Integer bankAccountTypeId, UUID organizationId);

}
