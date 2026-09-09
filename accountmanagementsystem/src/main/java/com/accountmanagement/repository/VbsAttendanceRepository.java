package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.VbsAttendance;

public interface VbsAttendanceRepository extends JpaRepository<VbsAttendance, UUID> {

    boolean existsByOrganizationIdAndStudentIdAndAttendanceDate(UUID organizationId, UUID studentId,
            LocalDate attendanceDate);

	Optional<VbsAttendance> findByIdAndOrganizationId(UUID attendanceId, UUID organizationId);

}
