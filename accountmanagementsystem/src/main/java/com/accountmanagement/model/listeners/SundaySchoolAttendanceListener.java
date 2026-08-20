package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.SundaySchoolAttendance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SundaySchoolAttendanceListener {

    @PrePersist
    public void onCreate(SundaySchoolAttendance sundaySchoolAttendance) {
        sundaySchoolAttendance.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SundaySchoolAttendance sundaySchoolAttendance) {
        sundaySchoolAttendance.setUpdatedAt(LocalDateTime.now());
    }

}
