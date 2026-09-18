package com.accountmanagement.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.BroadcastDelivery;

public interface BroadcastDeliveryRepository extends JpaRepository<BroadcastDelivery, UUID> {

    Optional<BroadcastDelivery> findByUserIdAndSentTo(UUID userId, String sentTo);

    boolean existsByBroadcastIdAndSentTo(UUID broadcastId, String sentTo);

}
