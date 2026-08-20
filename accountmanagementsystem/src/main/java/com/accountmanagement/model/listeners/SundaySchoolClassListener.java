package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.SundaySchoolClass;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SundaySchoolClassListener {

    @PrePersist
    public void onCreate(SundaySchoolClass sundaySchoolClass) {
        sundaySchoolClass.setStatus(AppConstants.ACTIVE);
        sundaySchoolClass.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SundaySchoolClass sundaySchoolClass) {
        sundaySchoolClass.setUpdatedAt(LocalDateTime.now());
    }

}
