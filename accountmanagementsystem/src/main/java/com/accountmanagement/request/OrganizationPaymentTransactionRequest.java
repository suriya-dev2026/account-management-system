package com.accountmanagement.request;

import java.math.BigDecimal;
import java.util.UUID;
import com.accountmanagement.enums.Gateway;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidCurrency;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationPaymentTransactionRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    private Gateway gateway;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    @Digits(integer = 10, fraction = 2, message = "Amount must have maximum 10 integer digits and 2 decimal digits.")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required.")
    @Size(max = 10, message = "Currency must not exceed 10 characters.")
    @ValidCurrency(message = "Currency must be a valid 3-letter ISO code (e.g. INR, USD, EUR)")
    private String currency;

    public void sanitizeInput() {
        setCurrency(Apputility.sanitizeInput(getCurrency()));
    }

}
