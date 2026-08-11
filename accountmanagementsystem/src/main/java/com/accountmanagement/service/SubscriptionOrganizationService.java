package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.exceptions.BusinessException;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionOrganizationMapper;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserVerification;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.request.SubscriptionOrganizationRequest;

@Service
public class SubscriptionOrganizationService {

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    private final OrganizationService organizationService;

    private final SubscriptionPlanService subscriptionPlanService;

    private final SubscriptionOrganizationMapper subscriptionOrganizationMapper;

    private final UserService userService;

    private final UserVerificationService userVerificationService;

    public SubscriptionOrganizationService(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            OrganizationService organizationService, SubscriptionPlanService subscriptionPlanService,
            SubscriptionOrganizationMapper subscriptionOrganizationMapper, UserService userService,
            UserVerificationService userVerificationService) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.organizationService = organizationService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.subscriptionOrganizationMapper = subscriptionOrganizationMapper;
        this.userService = userService;
        this.userVerificationService = userVerificationService;
    }

    @Transactional
    public SubscriptionOrganization createSubscription(SubscriptionOrganizationRequest request) {
        organizationService.findById(request.getOrganizationId());
        User user = userService.findByOrganizationId(request.getOrganizationId());
        UserVerification userVerification = userVerificationService
                .findByUserId(user.getId());
        if (!userVerification.getIsEmailVerified()) {
            throw new BusinessException(
                    "Email must be verified before creating a subscription");
        }
        subscriptionPlanService.findBySubscriptionPlanId(request.getPlanId());
        subscriptionOrganizationRepository.findByOrganizationIdAndStatus(
                request.getOrganizationId(),
                SubscriptionOrganizationStatus.ACTIVE)
                .ifPresent(subscription -> {
                    throw new DuplicateRecordException(
                            "Organization already has an active subscription");
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

}
