package com.accountmanagement.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.service.SubscriptionOrganizationService;

public class SubscriptionExpiryScheduler {

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;
    private final SubscriptionOrganizationService subscriptionOrganizationService;

    public SubscriptionExpiryScheduler(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            SubscriptionOrganizationService subscriptionOrganizationService) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.subscriptionOrganizationService = subscriptionOrganizationService;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void checkSubscriptionExpiry() {

        LocalDate today = LocalDate.now();

        sendExpiryReminder(today.plusDays(7), 7);

        sendExpiryReminder(today.plusDays(3), 3);

        sendExpiryReminder(today.plusDays(1), 1);

        subscriptionOrganizationService.expireSubscriptions(today);
    }

    private void sendExpiryReminder(LocalDate expiryDate, int daysBefore) {

        List<SubscriptionOrganization> subscriptions = subscriptionOrganizationRepository
                .findExpiringSubscriptions(expiryDate, "ACTIVE");
        for (SubscriptionOrganization subscription : subscriptions) {
            subscriptionOrganizationService.sendExpiryReminder(subscription, daysBefore);
        }
    }

}
