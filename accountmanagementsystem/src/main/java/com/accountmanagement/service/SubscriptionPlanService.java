package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.repository.SubscriptionPlanRepository;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    public List<SubscriptionPlan> viewAllSubscriptionPlan() {
        return subscriptionPlanRepository.findAll();
    }

    public SubscriptionPlan findBySubscriptionPlanId(UUID id){
        return subscriptionPlanRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Subscription plan id not found"));
    }
}
