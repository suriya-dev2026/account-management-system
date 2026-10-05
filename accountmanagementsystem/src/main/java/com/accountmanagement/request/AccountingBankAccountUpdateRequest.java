package com.accountmanagement.request;

import java.math.BigDecimal;

import com.accountmanagement.utility.Apputility;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountingBankAccountUpdateRequest {

    @NotNull(message = "Bank Account Type Id Is Required")
    private Integer bankAccountTypeId;

    @NotBlank(message = "Bank name is required.")
    @Size(max = 100, message = "Bank name must not exceed 100 characters.")
    private String bankName;

    @NotBlank(message = "Branch is required.")
    @Size(max = 100, message = "Branch must not exceed 100 characters.")
    private String branch;

    @NotBlank(message = "Account number is required.")
    @Pattern(regexp = "^[0-9]{9,18}$", message = "Account number must contain 9 to 18 digits.")
    private String accountNumber;

    @NotBlank(message = "IFSC is required.")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code.")
    private String ifsc;

    @NotBlank(message = "Account holder name is required.")
    @Size(max = 100, message = "Account holder name must not exceed 100 characters.")
    private String accountHolderName;

    @NotNull(message = "Opening balance is required.")
    @DecimalMin(value = "0.00", message = "Opening balance cannot be negative.")
    @Digits(integer = 12, fraction = 2, message = "Opening balance must have maximum 12 digits and 2 decimal places.")
    private BigDecimal openingBalance;

    @DecimalMin(value = "0.00", message = "Current balance cannot be negative.")
    @Digits(integer = 12, fraction = 2, message = "Current balance must have maximum 12 digits and 2 decimal places.")
    private BigDecimal currentBalance;

    public void sanitizeInput() {
        setBankName(Apputility.sanitizeInput(getBankName()));
        setBranch(Apputility.sanitizeInput(getBranch()));
        setAccountNumber(Apputility.sanitizeInput(getAccountNumber()));
        setAccountHolderName(Apputility.sanitizeInput(getAccountHolderName()));
    }

}
