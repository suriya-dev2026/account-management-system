package com.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.accountmanagement.model.SubscriptionUsage;
import com.accountmanagement.repository.SubscriptionUsageRepository;

@Service
public class SubscriptionUsageService {

    private final SubscriptionUsageRepository subscriptionUsageRepository;

    public SubscriptionUsageService(SubscriptionUsageRepository subscriptionUsageRepository) {
        this.subscriptionUsageRepository = subscriptionUsageRepository;
    }

    public SubscriptionUsage createSubscriptionUsage(UUID subscriptionOrganizationId) {
        SubscriptionUsage usage = new SubscriptionUsage();
        usage.setSubscriptionOrganizationId(subscriptionOrganizationId);
        usage.setCurrentMembers(0);
        usage.setCurrentTeachers(0);
        usage.setCurrentAdmins(1);
        usage.setStorageUsedMb(0);
        usage.setUpdatedAt(LocalDateTime.now());
        return subscriptionUsageRepository.save(usage);
    }
}
