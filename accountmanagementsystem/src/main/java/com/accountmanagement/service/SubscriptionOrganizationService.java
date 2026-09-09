package com.accountmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.enums.BillingCycle;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.enums.UserType;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionOrganizationMapper;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.model.User;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.repository.UserRepository;
import com.accountmanagement.request.SubscriptionOrganizationRequest;
import com.accountmanagement.response.ApiResponse;

@Service
public class SubscriptionOrganizationService {

    private final EmailQueueService emailQueueService;

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    private final OrganizationService organizationService;

    private final SubscriptionPlanService subscriptionPlanService;

    private final SubscriptionOrganizationMapper subscriptionOrganizationMapper;

    private final UserService userService;

    private final UserRepository userRepository;

    public SubscriptionOrganizationService(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            OrganizationService organizationService, SubscriptionPlanService subscriptionPlanService,
            SubscriptionOrganizationMapper subscriptionOrganizationMapper, UserService userService,
            EmailQueueService emailQueueService, UserRepository userRepository) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.organizationService = organizationService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.subscriptionOrganizationMapper = subscriptionOrganizationMapper;
        this.userService = userService;
        this.emailQueueService = emailQueueService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ApiResponse createSubscription(
            SubscriptionOrganizationRequest request) {
        organizationService.findById(request.getOrganizationId());
        SubscriptionPlan plan = subscriptionPlanService.findBySubscriptionPlanId(request.getPlanId());
        validateExistingSubscription(request.getOrganizationId());
        SubscriptionOrganization subscription = createSubscriptionOrganization(request);
        if (isFreePlan(plan)) {
            activateFreePlan(subscription, request.getOrganizationId());
            return new ApiResponse(AppConstants.SUCCESS,
                    "Free plan activated. Temporary credentials have been sent to your email.", 200);
        }
        createPendingSubscription(subscription);
        return new ApiResponse(AppConstants.SUCCESS, "Please make the payment to activate your subscription.", 200);
    }

    private void validateExistingSubscription(UUID organizationId) {
        LocalDate today = LocalDate.now();
        subscriptionOrganizationRepository
                .findByOrganizationIdAndStatus(
                        organizationId,
                        SubscriptionOrganizationStatus.ACTIVE)
                .ifPresent(subscription -> {
                    if (subscription.getEndDate() != null
                            && !subscription.getEndDate().isBefore(today)) {

                        throw new DuplicateRecordException(
                                "Organization already has an active subscription");
                    }
                    subscription.setStatus(
                            SubscriptionOrganizationStatus.EXPIRED);
                    subscriptionOrganizationRepository.save(subscription);
                });
        subscriptionOrganizationRepository
                .findByOrganizationIdAndStatus(
                        organizationId,
                        SubscriptionOrganizationStatus.PENDING)
                .ifPresent(subscription -> {
                    throw new DuplicateRecordException(
                            "Organization already selected a subscription plan");
                });
    }

    private SubscriptionOrganization createSubscriptionOrganization(
            SubscriptionOrganizationRequest request) {
        return subscriptionOrganizationMapper
                .toCreateSubscriptionOrganization(request);
    }

    private boolean isFreePlan(SubscriptionPlan plan) {
        return "FREEPLAN".equalsIgnoreCase(plan.getCode());
    }

    private SubscriptionOrganization activateFreePlan(SubscriptionOrganization subscription,
            UUID organizationId) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateEndDate(
                startDate,
                subscription.getBillingCycle());
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setStatus(
                SubscriptionOrganizationStatus.ACTIVE);
        SubscriptionOrganization savedSubscription = subscriptionOrganizationRepository.save(subscription);
        System.out.println("FREE PLAN SAVED");
        sendTemporaryCredentials(organizationId);
        System.out.println("TEMPORARY CREDENTIALS CREATED");
        return savedSubscription;
    }

    private void sendTemporaryCredentials(UUID organizationId) {
        User user = userRepository
                .findByOrganizationIdAndUserType(
                        organizationId,
                        UserType.SUPERADMIN)
                .orElseThrow(() -> new RecordNotFoundException(
                        "Superadmin user not found"));
        userService.createTemporaryCredentials(user.getId());
    }

    private SubscriptionOrganization createPendingSubscription(
            SubscriptionOrganization subscription) {
        subscription.setStatus(
                SubscriptionOrganizationStatus.PENDING);
        return subscriptionOrganizationRepository.save(subscription);
    }

    private LocalDate calculateEndDate(LocalDate startDate, BillingCycle billingCycle) {
        return switch (billingCycle) {
            case MONTHLY ->
                startDate.plusMonths(1).minusDays(1);
            case QUARTERLY ->
                startDate.plusMonths(3).minusDays(1);
            case HALFYEARLY ->
                startDate.plusMonths(6).minusDays(1);
            case YEARLY ->
                startDate.plusYears(1).minusDays(1);
        };
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
        User user = userService.findByOrganizationIdAndUserType(subscription.getOrganizationId(), UserType.SUPERADMIN);

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
        User user = userService.findByOrganizationIdAndUserType(subscription.getOrganizationId(), UserType.SUPERADMIN);
        if (user == null) {
            return;
        }
        String body = SubscriptionMessage.SUBSCRIPTION_EXPIRED_TEMPLATE
                .replace("{{userName}}", user.getUserName())
                .replace("{{expiryDate}}", subscription.getEndDate().toString());

        emailQueueService.addToEmailQueue(
                user.getId(),
                user.getEmail(), body);
    }

}
