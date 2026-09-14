package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.MeetingAttendance;

public interface MeetingAttendanceRepository extends JpaRepository<MeetingAttendance, UUID> {

    boolean existsByOrganizationIdAndMemberIdAndAttendanceDate(UUID organizationId, UUID memberId,
            LocalDate attendanceDate);

}
