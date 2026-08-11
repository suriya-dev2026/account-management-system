package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.SubscriptionOrganizationMapper;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.repository.SubscriptionOrganizationRepository;
import com.accountmanagement.request.SubscriptionOrganizationRequest;

@Service
public class SubscriptionOrganizationService {

    private final SubscriptionOrganizationRepository subscriptionOrganizationRepository;

    private final OrganizationService organizationService;

    private final SubscriptionPlanService subscriptionPlanService;

    private final SubscriptionOrganizationMapper subscriptionOrganizationMapper;

    public SubscriptionOrganizationService(SubscriptionOrganizationRepository subscriptionOrganizationRepository,
            OrganizationService organizationService, SubscriptionPlanService subscriptionPlanService,
            SubscriptionOrganizationMapper subscriptionOrganizationMapper) {
        this.subscriptionOrganizationRepository = subscriptionOrganizationRepository;
        this.organizationService = organizationService;
        this.subscriptionPlanService = subscriptionPlanService;
        this.subscriptionOrganizationMapper = subscriptionOrganizationMapper;
    }

    @Transactional
    public SubscriptionOrganization createSubscription(SubscriptionOrganizationRequest request) {
        organizationService.findById(request.getOrganizationId());
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
