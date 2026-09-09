package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.SundaySchoolStudent;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SundaySchoolStudentListener {

    @PrePersist
    public void onCreate(SundaySchoolStudent sundaySchoolStudent) {
        sundaySchoolStudent.setStatus(AppConstants.ACTIVE);
        sundaySchoolStudent.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SundaySchoolStudent sundaySchoolStudent) {
        sundaySchoolStudent.setUpdatedAt(LocalDateTime.now());
    }

}
