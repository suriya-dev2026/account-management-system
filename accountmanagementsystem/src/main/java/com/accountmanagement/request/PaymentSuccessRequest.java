package com.accountmanagement.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentSuccessRequest {

    @NotBlank(message = "Payment reference is required")
    private String paymentReference;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
}
