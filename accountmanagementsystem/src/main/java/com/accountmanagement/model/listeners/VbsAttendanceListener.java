package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.VbsAttendance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class VbsAttendanceListener {

    @PrePersist
    public void onCreate(VbsAttendance vbsAttendance) {
        vbsAttendance.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(VbsAttendance vbsAttendance) {
        vbsAttendance.setUpdatedAt(LocalDateTime.now());
    }

}
