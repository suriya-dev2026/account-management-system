package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.Location;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AreaListeners {

    @PrePersist
    public void onCreate(Location area) {
        area.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(Location area) {
        area.setUpdatedAt(LocalDateTime.now());
    }

}
