package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.Location;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class LocationListeners {

    @PrePersist
    public void onCreate(Location location) {
        location.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(Location location) {
        location.setUpdatedAt(LocalDateTime.now());
    }

}
