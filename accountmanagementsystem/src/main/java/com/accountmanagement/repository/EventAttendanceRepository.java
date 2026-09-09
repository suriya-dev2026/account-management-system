package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.EventAttendance;

public interface EventAttendanceRepository extends  JpaRepository<EventAttendance, UUID>{

    boolean existsByOrganizationIdAndEventParticipantIdAndAttendanceDate(UUID organizationId, UUID eventParticipantId,
            LocalDate attendanceDate);

}
