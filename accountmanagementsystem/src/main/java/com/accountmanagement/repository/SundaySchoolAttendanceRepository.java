package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.SundaySchoolAttendance;

public interface SundaySchoolAttendanceRepository extends JpaRepository<SundaySchoolAttendance, UUID> {

    Optional<SundaySchoolAttendance> findByOrganizationIdAndStudentIdAndAttendanceDate(UUID organizationId,
            UUID studentId,
            LocalDate attendanceDate);

    boolean existsByOrganizationIdAndStudentIdAndAttendanceDate(UUID organizationId, UUID studentId,
            LocalDate attendanceDate);

}
