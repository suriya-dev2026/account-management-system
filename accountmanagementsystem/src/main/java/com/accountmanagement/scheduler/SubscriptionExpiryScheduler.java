package com.accountmanagement.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.service.SubscriptionOrganizationService;

@Component
public class SubscriptionExpiryScheduler {

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;
    private final SubscriptionOrganizationService subscriptionOrganizationService;

    public SubscriptionExpiryScheduler(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            SubscriptionOrganizationService subscriptionOrganizationService) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.subscriptionOrganizationService = subscriptionOrganizationService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void checkSubscriptionExpiry() {

        LocalDate today = LocalDate.now();

        List<SubscriptionOrganization> subscriptions = subscriptionOrganizationRepository
                .findSubscriptionsExpiringWithinFiveDays(today, today.plusDays(5),
                        SubscriptionOrganizationStatus.ACTIVE);

        for (SubscriptionOrganization subscription : subscriptions) {
            long daysRemaining = ChronoUnit.DAYS.between(today, subscription.getEndDate());

            if (daysRemaining == 0) {

                subscriptionOrganizationService.sendExpiredMail(subscription);

                subscription.setStatus(SubscriptionOrganizationStatus.EXPIRED);

                subscription.setUpdatedAt(LocalDateTime.now());

                subscriptionOrganizationRepository.save(subscription);

            } else {
                subscriptionOrganizationService.sendExpiryReminder(subscription, (int) daysRemaining);
            }
        }
    }

}
