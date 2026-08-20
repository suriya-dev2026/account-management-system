package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.model.SubscriptionOrganization;

public interface SubscriptionOrganizationRepository extends JpaRepository<SubscriptionOrganization, UUID> {

        boolean existsByOrganizationIdAndStatus(UUID id, String string);

        Optional<SubscriptionOrganization> findByOrganizationIdAndStatus(UUID organizationId,
                        SubscriptionOrganizationStatus status);

        @Query("""
                        SELECT s FROM SubscriptionOrganization s
                        WHERE s.endDate BETWEEN :today AND :fiveDaysLater
                        AND s.status = :status
                        """)
        List<SubscriptionOrganization> findSubscriptionsExpiringWithinFiveDays(
                        @Param("today") LocalDate today,
                        @Param("fiveDaysLater") LocalDate fiveDaysLater,
                        @Param("status") SubscriptionOrganizationStatus status);

}
