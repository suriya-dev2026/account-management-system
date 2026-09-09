package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.EventParticipant;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, UUID> {

    boolean existsByEventIdAndFullNameAndContactNumber(UUID eventId, String fullName, String contactNumber);

}
