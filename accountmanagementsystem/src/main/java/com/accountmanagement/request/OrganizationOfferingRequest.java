package com.accountmanagement.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.enums.GatewayStatus;
import com.accountmanagement.validations.ValidCurrency;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationOfferingRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotNull(message = "Offering Type Id Is Required")
    private Integer offeringTypeId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    private UUID memberId;

    private UUID paymentTransactionId;

    @NotNull(message = "Payment Method Id Is Required")
    private Integer paymentMethodId;

    @NotNull(message = "Amount Is Required")
    @DecimalMin(value = "0.01", message = "Amount Must Be Greater Than Zero")
    private BigDecimal amount;

    @ValidCurrency(message = "Currency must be a valid 3-letter ISO code (e.g. INR, USD, EUR)")
    @ValidInput(message = "Currency Contains Invalid Characters")
    private String currency;

    private UUID bankAccountId;

    private GatewayStatus gatewayStatus;

    @NotNull(message = "Received Date Is Required")
    private LocalDate receivedDate;

    @ValidInput(message = "Remarks Contains Invalid Characters")
    private String remarks;

}
