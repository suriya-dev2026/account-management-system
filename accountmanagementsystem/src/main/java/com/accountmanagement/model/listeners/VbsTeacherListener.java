package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.VbsTeacher;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class VbsTeacherListener {

    @PrePersist
    public void onCreate(VbsTeacher vbsTeacher) {
        vbsTeacher.setStatus(AppConstants.ACTIVE);
        vbsTeacher.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(VbsTeacher vbsTeacher) {
        vbsTeacher.setUpdatedAt(LocalDateTime.now());
    }
}
