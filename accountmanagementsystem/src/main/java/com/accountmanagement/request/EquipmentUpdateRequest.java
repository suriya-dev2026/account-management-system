package com.accountmanagement.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidEquipmentCategoryId;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentUpdateRequest {

    @ValidInput(message = "Equipment Name Contains Invalid Characters")
    @NotBlank(message = "Equipment Name Is Required")
    private String equipmentName;

    @ValidEquipmentCategoryId(message = "Equipment Category Id Does Not Exists")
    @NotNull(message = "Category Id Is Required")
    private Integer categoryId;

    @NotNull(message = "Purchase Date Is Required")
    private LocalDate purchaseDate;

    @DecimalMin(value = "0.01", message = "Purchase cost must be greater than 0")
    @Digits(integer = 13, fraction = 2, message = "Purchase cost must have maximum 13 digits and 2 decimal places")
    private BigDecimal purchaseCost;

    private LocalDate warrantyExpiryDate;

    @ValidInput(message = "Vendor Details Contains Invalid Characters")
    private String vendorDetails;

    public void sanitizeInput() {
        setEquipmentName(Apputility.sanitizeInput(getEquipmentName()));
        setVendorDetails(Apputility.sanitizeInput(getVendorDetails()));
    }
}
