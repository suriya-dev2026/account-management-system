package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.request.SubscriptionFeatureRequest;

@Component
public class SubscriptionFeatureMapper {

    public SubscriptionFeature toCreateSubscriptionFeature(String code,
            SubscriptionFeatureRequest subscriptionFeatureRequest) {
        SubscriptionFeature subscriptionFeature = new SubscriptionFeature();
        subscriptionFeature.setCode(code);
        subscriptionFeature.setName(subscriptionFeatureRequest.getName());
        subscriptionFeature.setDescription(subscriptionFeatureRequest.getDescription());
        subscriptionFeature.setStatus(AppConstants.ACTIVE);
        return subscriptionFeature;
    }

    public SubscriptionFeature toUpdateSubscriptionFeature(SubscriptionFeature subscriptionFeature,
            SubscriptionFeatureRequest subscriptionFeatureRequest) {
        subscriptionFeature.setName(subscriptionFeatureRequest.getName());
        subscriptionFeature.setDescription(subscriptionFeatureRequest.getDescription());
        subscriptionFeature.setStatus(AppConstants.ACTIVE);
        return subscriptionFeature;
    }

}
