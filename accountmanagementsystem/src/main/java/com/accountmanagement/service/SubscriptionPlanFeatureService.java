package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.SubscriptionPlanFeature;
import com.accountmanagement.repository.SubscriptionFeatureRepository;
import com.accountmanagement.repository.SubscriptionPlanFeatureRepository;
import com.accountmanagement.repository.SubscriptionPlanRepository;
import com.accountmanagement.request.SubscriptionPlanFeatureRequest;
import com.accountmanagement.request.UpdateSubscriptionPlanFeatureRequest;

@Service
public class SubscriptionPlanFeatureService {

    private final SubscriptionPlanFeatureRepository subscriptionPlanFeatureRepository;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final SubscriptionFeatureRepository subscriptionFeatureRepository;

    public SubscriptionPlanFeatureService(SubscriptionPlanFeatureRepository subscriptionPlanFeatureRepository,
            SubscriptionPlanRepository subscriptionPlanRepository,
            SubscriptionFeatureRepository subscriptionFeatureRepository) {
        this.subscriptionPlanFeatureRepository = subscriptionPlanFeatureRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subscriptionFeatureRepository = subscriptionFeatureRepository;
    }

    @Transactional
    public String addSubscriptionPlanFeature(SubscriptionPlanFeatureRequest subscriptionPlanFeatureRequest) {
        validateSubscriptionPlanFeature(subscriptionPlanFeatureRequest);
        for (UUID featureId : subscriptionPlanFeatureRequest.getFeatureId()) {
            boolean exists = subscriptionPlanFeatureRepository
                    .existsByPlanIdAndFeatureId(subscriptionPlanFeatureRequest.getPlanId(), featureId);
            if (exists) {
                throw new DuplicateRecordException("Feature is already assigned to this subscription plan.");
            }
            SubscriptionPlanFeature planFeature = new SubscriptionPlanFeature();
            planFeature.setPlanId(subscriptionPlanFeatureRequest.getPlanId());
            planFeature.setFeatureId(featureId);
            subscriptionPlanFeatureRepository.save(planFeature);
        }
        return "Plan Features added successfully.";
    }

    @Transactional
    public String updateSubscriptionPlanFeature(UUID planId,
            UpdateSubscriptionPlanFeatureRequest request) {
        SubscriptionPlanFeature planFeature = subscriptionPlanFeatureRepository
                .findByPlanIdAndFeatureId(planId, request.getFeatureId())
                .orElseThrow(() -> new RecordNotFoundException(
                        "Plan feature not found."));
        if (subscriptionPlanFeatureRepository.existsByPlanIdAndFeatureId(planId,
                request.getNewFeatureId())) {
            throw new DuplicateRecordException(
                    "New feature is already assigned to this subscription plan.");
        }
        planFeature.setFeatureId(request.getNewFeatureId());
        subscriptionPlanFeatureRepository.save(planFeature);
        return "Plan Feature updated successfully.";
    }

    public SubscriptionPlanFeature findBySubscriptionPlanFeatureId(UUID id) {
        return subscriptionPlanFeatureRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Subscription feature id not found"));
    }

    public void deleteSubscriptionPlanFeatureById(UUID id) {
        findBySubscriptionPlanFeatureId(id);
        subscriptionPlanFeatureRepository.deleteById(id);
    }

    public List<SubscriptionPlanFeature> viewAllSubscriptionPlanFeature() {
        return subscriptionPlanFeatureRepository.findAll();
    }

    private void existsBySubscriptionPlanId(UUID planId) {
        if (!subscriptionPlanRepository.existsById(planId)) {
            throw new RecordNotFoundException(
                    "Subscription plan id does not exists.");
        }
    }

    private void validateSubscriptionPlanFeature(SubscriptionPlanFeatureRequest request) {
        existsBySubscriptionPlanId(request.getPlanId());
        for (UUID featureId : request.getFeatureId()) {
            existsBySubscriptionFeatureId(featureId);
        }
    }

    private void existsBySubscriptionFeatureId(UUID featureId) {
        if (!subscriptionFeatureRepository.existsById(featureId)) {
            throw new RecordNotFoundException("Subscription feature not found.");
        }
    }

}
