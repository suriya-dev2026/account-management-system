package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentCategoryRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Category Name Is Required")
    @ValidInput(message = "Category Name Contains Invalid Characters")
    private String categoryName;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    public void sanitizeInput() {
        setCategoryName(Apputility.sanitizeInput(getCategoryName()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }
}
