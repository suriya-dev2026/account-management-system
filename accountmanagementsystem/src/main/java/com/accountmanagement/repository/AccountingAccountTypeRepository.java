package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.AccountingAccountType;

public interface AccountingAccountTypeRepository extends JpaRepository<AccountingAccountType, Integer> {

    boolean existsByOrganizationIdAndTypeName(UUID organizationId, String typeName);

}
