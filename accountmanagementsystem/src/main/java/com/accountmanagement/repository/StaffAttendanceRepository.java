package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.StaffAttendance;

public interface StaffAttendanceRepository extends JpaRepository<StaffAttendance, UUID> {

    boolean existsByOrganizationIdAndStaffIdAndAttendanceDate(UUID organizationId, UUID staffId,
            LocalDate attendanceDate);

}
