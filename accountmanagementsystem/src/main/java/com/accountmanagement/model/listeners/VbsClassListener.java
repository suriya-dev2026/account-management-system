package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.VbsClass;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class VbsClassListener {

    @PrePersist
    public void onCreate(VbsClass vbsClass) {

        vbsClass.setStatus(AppConstants.ACTIVE);
        vbsClass.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(VbsClass vbsClass) {
        vbsClass.setUpdatedAt(LocalDateTime.now());
    }

}
