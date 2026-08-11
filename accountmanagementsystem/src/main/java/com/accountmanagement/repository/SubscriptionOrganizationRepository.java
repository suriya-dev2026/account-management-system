package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.model.SubscriptionOrganization;

public interface SubscriptionOrganizationRepository extends JpaRepository<SubscriptionOrganization, UUID> {

    boolean existsByOrganizationIdAndStatus(UUID id, String string);

    Optional<SubscriptionOrganization> findByOrganizationIdAndStatus(UUID organizationId,
            SubscriptionOrganizationStatus status);

}
