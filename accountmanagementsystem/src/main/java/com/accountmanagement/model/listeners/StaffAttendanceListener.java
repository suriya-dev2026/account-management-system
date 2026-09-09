package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.StaffAttendance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data 
public class StaffAttendanceListener {

    @PrePersist 
    public void onCreateStaffAttendance(StaffAttendance staffAttendance) {
        staffAttendance.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate 
    public void onUpdateStaffAttendance(StaffAttendance staffAttendance) {
        staffAttendance.setUpdatedAt(LocalDateTime.now());
    }

}
