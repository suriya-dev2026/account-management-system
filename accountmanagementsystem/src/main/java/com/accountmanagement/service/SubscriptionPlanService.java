package com.accountmanagement.service;

import com.accountmanagement.mapper.SubscriptionPlanMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.repository.SubscriptionPlanRepository;
import com.accountmanagement.request.SubscriptionPlanRequest;

@Service
public class SubscriptionPlanService {

    private final SubscriptionPlanMapper subscriptionPlanMapper;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository,
            SubscriptionPlanMapper subscriptionPlanMapper) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subscriptionPlanMapper = subscriptionPlanMapper;
    }

    public SubscriptionPlan addSubscriptionPlan(SubscriptionPlanRequest subscriptionPlanRequest) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toAddSubscriptionPlan(subscriptionPlanRequest);
        return subscriptionPlanRepository.save(subscriptionPlan);
    }

     public List<SubscriptionPlan> viewAllSubscriptionPlan() {
        return subscriptionPlanRepository.findAll();
    }
}
