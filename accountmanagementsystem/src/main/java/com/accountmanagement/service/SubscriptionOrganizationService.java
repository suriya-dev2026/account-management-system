package com.accountmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.constants.message.SubscriptionMessage;
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
        String expiryMessage;
        if (daysBefore == 1) {
            expiryMessage = "tomorrow";
        } else {
            expiryMessage = "in " + daysBefore + " days";
        }
        String body = SubscriptionMessage.SUBSCRIPTION_EXPIRY_REMINDER_TEMPLATE
                .replace("{{userName}}", user.getUserName())
                .replace("{{expiryMessage}}", expiryMessage)
                .replace("{{expiryDate}}", subscription.getEndDate().toString());

        emailQueueService.addToEmailQueue(user.getId(), user.getEmail(), body);
    }

    @Transactional
    public void sendExpiredMail(SubscriptionOrganization subscription) {
        User user = userService.findByOrganizationIdAndUserType(subscription.getOrganizationId(), "SUPERADMIN");

        if (user == null) {
            return;
        }

        String body = SubscriptionMessage.SUBSCRIPTION_EXPIRED_TEMPLATE
                .replace("{{userName}}", user.getUserName())
                .replace("{{expiryDate}}", subscription.getEndDate().toString());

        emailQueueService.addToEmailQueue(
                user.getId(),
                user.getEmail(), body);
        System.out.println("Mail added to EmailQueue");
    }

}
