package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.SundaySchoolTeacher;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SundaySchoolTeacherListener {

    @PrePersist
    public void onCreate(SundaySchoolTeacher sundaySchoolTeacher) {
        sundaySchoolTeacher.setStatus(AppConstants.ACTIVE);
        sundaySchoolTeacher.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SundaySchoolTeacher sundaySchoolTeacher) {
        sundaySchoolTeacher.setUpdatedAt(LocalDateTime.now());
    }
}
