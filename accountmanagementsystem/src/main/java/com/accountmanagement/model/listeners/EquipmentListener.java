package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Equipment;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class EquipmentListener {

    @PrePersist
    public void onCreateEquipment(Equipment equipment) {
        equipment.setStatus(AppConstants.ACTIVE);
        equipment.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateBulletin(Equipment equipment) {
        equipment.setUpdatedAt(LocalDateTime.now());
    }
}
