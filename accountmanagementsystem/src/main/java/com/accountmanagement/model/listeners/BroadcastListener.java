package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Broadcast;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class BroadcastListener {

    @PrePersist
    public void onCreateBroadcast(Broadcast broadcast) {
        broadcast.setStatus(AppConstants.ACTIVE);
        broadcast.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdateBroadcast(Broadcast broadcast) {
        broadcast.setUpdatedAt(LocalDateTime.now());
    }
}
