package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.BroadcastDelivery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class BroadcastDeliveryListener {

    @PrePersist
    public void onCreateBroadcastDelivery(BroadcastDelivery broadcastDelivery) {
        broadcastDelivery.setStatus(AppConstants.ACTIVE);
        broadcastDelivery.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateBroadcastDelivery(BroadcastDelivery broadcastDelivery) {
        broadcastDelivery.setUpdatedAt(LocalDateTime.now());
    }

}
