package com.accountmanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionOrganizationMapper;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.request.SubscriptionOrganizationRequest;

@Service
public class SubscriptionOrganizationService {

    private final EmailQueueService emailQueueService;

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    private final OrganizationService organizationService;

    private final SubscriptionPlanService subscriptionPlanService;

    private final SubscriptionOrganizationMapper subscriptionOrganizationMapper;

    private final UserService userService;

    public SubscriptionOrganizationService(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            OrganizationService organizationService, SubscriptionPlanService subscriptionPlanService,
            SubscriptionOrganizationMapper subscriptionOrganizationMapper, UserService userService,
            EmailQueueService emailQueueService) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.organizationService = organizationService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.subscriptionOrganizationMapper = subscriptionOrganizationMapper;
        this.userService = userService;
        this.emailQueueService = emailQueueService;
    }

    @Transactional
    public SubscriptionOrganization createSubscription(SubscriptionOrganizationRequest request) {
        organizationService.findById(request.getOrganizationId());
        subscriptionPlanService.findBySubscriptionPlanId(request.getPlanId());
        subscriptionOrganizationRepository
                .findByOrganizationIdAndStatus(request.getOrganizationId(), SubscriptionOrganizationStatus.ACTIVE)
                .ifPresent(subscription -> {
                    if (!subscription.getEndDate().isBefore(LocalDate.now())) {
                        throw new DuplicateRecordException("Organization already has an active subscription");
                    }
                    subscription.setStatus(SubscriptionOrganizationStatus.EXPIRED);
                    subscriptionOrganizationRepository.save(subscription);
                });
        subscriptionOrganizationRepository
                .findByOrganizationIdAndStatus(request.getOrganizationId(), SubscriptionOrganizationStatus.PENDING)
                .ifPresent(subscription -> {
                    throw new DuplicateRecordException("Organization already select a subscription plan");
                });
        SubscriptionOrganization subscriptionOrganization = subscriptionOrganizationMapper
                .toCreateSubscriptionOrganization(request);
        return subscriptionOrganizationRepository.save(subscriptionOrganization);
    }

    public List<SubscriptionOrganization> viewAllSubscriptionOrganization() {
        return subscriptionOrganizationRepository.findAll();
    }

    public SubscriptionOrganization findBySubscriptionOrganizationId(UUID id) {
        return subscriptionOrganizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Subscription Organization id not found"));
    }

    @Transactional
    public void sendExpiryReminder(SubscriptionOrganization subscription, int daysBefore) {
        User user = userService.findByOrganizationIdAndUserType(subscription.getOrganizationId(), "SUPERADMIN");

        if (user == null) {
            return;
        }
        String body;

        if (daysBefore == 1) {

            body = """
                    Hello %s,Your subscription will expire tomorrow.Subscription expiry date: %s
                    Please renew your subscription before the expiry date to continue using the services without interruption.
                    Thank you.
                    """
                    .formatted(user.getUserName(), subscription.getEndDate());
        } else {

            body = """
                    Hello %s, Your subscription will expire in %d days.Subscription expiry date: %s
                    Please renew your subscription before the expiry date to continue using the services without interruption.
                    Thank you.
                    """
                    .formatted(user.getUserName(), daysBefore, subscription.getEndDate());
        }
        emailQueueService.addToEmailQueue(user.getId(), user.getEmail(), body);
    }

    @Transactional
    public void expireSubscriptions(LocalDate today) {
        List<SubscriptionOrganization> subscriptions = subscriptionOrganizationRepository.findExpiredSubscriptions(
                today, "ACTIVE");
        for (SubscriptionOrganization subscription : subscriptions) {
            subscription.setStatus(SubscriptionOrganizationStatus.EXPIRED);
            subscription.setUpdatedAt(LocalDateTime.now());
            subscriptionOrganizationRepository.save(subscription);
        }
    }

}
