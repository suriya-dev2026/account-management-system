package com.accountmanagement.request;

import java.math.BigDecimal;
import java.util.UUID;
import com.accountmanagement.enums.PaymentType;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidSubscriptionOrganizationId;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionPaymentRequest {

    @NotNull(message = "subscription organization id is required")
    @ValidSubscriptionOrganizationId(message = "subscription organization id does not exists")
    private UUID subscriptionOrganizationId;

    @NotNull(message = "payment type is required")
    private PaymentType paymentType;

    @DecimalMin(value = "0.0", message = "Discount percentage cannot be less than 0")
    @DecimalMax(value = "100.0", message = "Discount percentage cannot be greater than 100")
    @NotNull(message = "default value 0.00")
    private BigDecimal discountPercentage;

    private String paymentReference;

    public void sanitizeInput() {
        setPaymentReference(Apputility.sanitizeInput(getPaymentReference()));
    }

}
