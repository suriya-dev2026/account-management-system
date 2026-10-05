package com.accountmanagement.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.accountmanagement.enums.Gateway;
import com.accountmanagement.validations.ValidOrganizationId;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountingPaymentTransactionRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Gateway Is Required")
    @Size(max = 50)
    private Gateway gateway;

    @NotBlank
    @Size(max = 100)
    private String transactionId;

    @Size(max = 100)
    private String gatewayReference;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;

    @NotBlank
    @Size(max = 10)
    private String currency;

    private JsonNode responseJson;

    @NotNull
    private LocalDateTime paymentTime;

    private Boolean verified;

    @NotBlank
    private String status;
}
