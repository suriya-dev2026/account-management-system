package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;

import com.accountmanagement.model.SubscriptionOrganization;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
public class SubscriptionOrganizationListener {

    @PrePersist
    public void onCreate(SubscriptionOrganization subscriptionOrganization) {
        subscriptionOrganization.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onUpdate(SubscriptionOrganization subscriptionOrganization) {
        subscriptionOrganization.setUpdatedAt(LocalDateTime.now());
    }
}
