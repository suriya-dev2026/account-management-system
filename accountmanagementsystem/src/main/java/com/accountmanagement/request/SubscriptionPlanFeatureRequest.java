package com.accountmanagement.request;

import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionPlanFeatureRequest {

    @NotNull(message = "plan id required")
    private UUID planId;

    @NotNull(message = "feature id required")
    private List<UUID> featureId;

}
