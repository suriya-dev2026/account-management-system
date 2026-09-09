package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.VbsStudent;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class VbsStudentListener {

    @PrePersist
    public void onCreate(VbsStudent vbsStudent) {

        vbsStudent.setStatus(AppConstants.ACTIVE);
        vbsStudent.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(VbsStudent vbsStudent) {
        vbsStudent.setUpdatedAt(LocalDateTime.now());
    }

}
