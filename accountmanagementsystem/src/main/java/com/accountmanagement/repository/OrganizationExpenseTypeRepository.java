package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationExpenseType;

public interface OrganizationExpenseTypeRepository extends JpaRepository<OrganizationExpenseType, Integer> {

    boolean existsByTypeName(String typeName);

    boolean existsByOrganizationIdAndTypeName(UUID organizationId, String typeName);

    Optional<OrganizationExpenseType> findByIdAndOrganizationId(Integer expenseTypeId, UUID organizationId);

}
