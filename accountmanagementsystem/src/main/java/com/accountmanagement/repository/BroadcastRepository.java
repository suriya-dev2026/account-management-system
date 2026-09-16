package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, UUID> {

    boolean existsByBroadcastTypeEqualsIgnoreCase(String broadcastType);

}
