package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.enums.SubscriptionOrganizationStatus;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.request.SubscriptionOrganizationRequest;

@Component
public class SubscriptionOrganizationMapper {

    public SubscriptionOrganization toCreateSubscriptionOrganization(
            SubscriptionOrganizationRequest subscriptionOrganizationRequest) {
        SubscriptionOrganization subscriptionOrganization = new SubscriptionOrganization();
        subscriptionOrganization.setOrganizationId(subscriptionOrganizationRequest.getOrganizationId());
        subscriptionOrganization.setPlanId(subscriptionOrganizationRequest.getPlanId());
        subscriptionOrganization.setBillingCycle(subscriptionOrganizationRequest.getBillingCycle());
        subscriptionOrganization.setStatus(SubscriptionOrganizationStatus.PENDING);
        subscriptionOrganization.setAutoRenew(subscriptionOrganizationRequest.getAutoRenew());
        return subscriptionOrganization;
    }
}
