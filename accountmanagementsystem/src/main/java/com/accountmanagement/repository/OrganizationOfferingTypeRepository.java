package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationOfferingType;

public interface OrganizationOfferingTypeRepository extends JpaRepository<OrganizationOfferingType, Integer> {

    boolean existsByTypeName(String typeName);

    Optional<OrganizationOfferingType> findByIdAndOrganizationId(Integer offeringTypeId, UUID organizationId);

}
