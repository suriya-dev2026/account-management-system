package com.accountmanagement.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationExpenseRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotNull(message = "Expense Type Id Is Required")
    private Integer expenseTypeId;

    private Integer paymentMethodId;

    @NotNull(message = "Amount Is Required")
    @DecimalMin(value = "0.01", message = "Amount Must Be Greater Than Zero")
    private BigDecimal amount;

    @NotNull(message = "Expense Date Is Required")
    private LocalDate expenseDate;

    @ValidInput(message = "Remarks Contains Invalid Characters")
    private String remarks;

    public void sanitizeInput() {
        setRemarks(Apputility.sanitizeInput(getRemarks()));
    }

}
