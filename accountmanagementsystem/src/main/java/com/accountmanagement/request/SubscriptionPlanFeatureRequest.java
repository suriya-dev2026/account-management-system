package com.accountmanagement.request;

import java.util.List;
import java.util.UUID;

import com.accountmanagement.validations.ValidFeatureId;
import com.accountmanagement.validations.ValidPlanId;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionPlanFeatureRequest {

    @NotNull(message = "Plan Id Required")
    @ValidPlanId(message = "Plan Id Does Not Exists")
    private UUID planId;

    @NotNull(message = "Feature Id Required")

    private List<@ValidFeatureId(message = "Feature Id Does Not Exists") UUID> featureId;

}
