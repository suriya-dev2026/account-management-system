package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.enums.BillingCycle;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidPlanId;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionOrganizationRequest {

        @ValidOrganizationId(message = "organization id does not exists")
        private UUID organizationId;

        @ValidPlanId(message = "subscription plan id does not exists")
        private UUID planId;

        @NotNull(message = "billing cycle is required")
        private BillingCycle billingCycle;

        @NotNull(message = "auto renew is required")
        private Boolean autoRenew;
}
