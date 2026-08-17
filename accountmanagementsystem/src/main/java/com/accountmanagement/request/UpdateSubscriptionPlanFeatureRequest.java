package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.validations.ValidFeatureId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSubscriptionPlanFeatureRequest {

    @NotNull(message = "Feature Id Is Required")
    @ValidFeatureId(message = "Feature Id Does Not Exists")
    private UUID featureId;

    @NotNull(message = "New Feature Id Is Required")
    @ValidFeatureId(message = "New Feature Id Does Not Exists")
    private UUID newFeatureId;

}
