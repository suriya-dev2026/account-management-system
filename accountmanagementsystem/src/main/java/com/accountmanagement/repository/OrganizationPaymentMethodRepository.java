package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.OrganizationPaymentMethod;

public interface OrganizationPaymentMethodRepository extends JpaRepository<OrganizationPaymentMethod, Integer> {

    boolean existsByOrganizationIdAndMethodName(UUID organizationId, String methodName);

    boolean existsByOrganizationIdAndCategory(UUID organizationId, String category);

    Optional<OrganizationPaymentMethod> findByIdAndOrganizationId(Integer paymentMethodId, UUID organizationId);

}
