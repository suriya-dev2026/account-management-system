package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Staff;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class StaffListener {

    private final JdbcTemplate jdbcTemplate;

    public StaffListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PrePersist
    public void onCreateStaff(Staff staff) {
        String code = generateStaffCode();
        staff.setStaffCode(code);
        staff.setStatus(AppConstants.ACTIVE);
        staff.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateStaff(Staff staff) {
        staff.setUpdatedAt(LocalDateTime.now());
    }

    private String generateStaffCode() {
        Long sequence = jdbcTemplate.queryForObject("select nextval('staff_code_seq')", Long.class);
        return String.format("STF-%05d", sequence);
    }

}
