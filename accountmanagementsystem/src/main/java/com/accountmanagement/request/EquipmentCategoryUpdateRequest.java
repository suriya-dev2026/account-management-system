package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipmentCategoryUpdateRequest {

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
