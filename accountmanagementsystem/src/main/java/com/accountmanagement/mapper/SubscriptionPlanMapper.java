package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.request.SubscriptionPlanRequest;

@Component
public class SubscriptionPlanMapper {

    public SubscriptionPlan toAddSubscriptionPlan(SubscriptionPlanRequest subscriptionPlanRequest) {

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setName(subscriptionPlanRequest.getName());
        subscriptionPlan.setDescription(subscriptionPlanRequest.getDescription());
        subscriptionPlan.setBillingCycle(subscriptionPlanRequest.getBillingCycle());
        subscriptionPlan.setPrice(subscriptionPlanRequest.getPrice());
        subscriptionPlan.setCurrency(subscriptionPlanRequest.getCurrency());
        subscriptionPlan.setTrialDays(subscriptionPlanRequest.getTrialDays());
        subscriptionPlan.setMaxStudents(subscriptionPlanRequest.getMaxStudents());
        subscriptionPlan.setMaxTeachers(subscriptionPlanRequest.getMaxTeachers());
        subscriptionPlan.setMaxAdmin(subscriptionPlanRequest.getMaxAdmin());
        subscriptionPlan.setDiscountPercentage(subscriptionPlanRequest.getDiscountPercentage());
        return subscriptionPlan;
    }
}
