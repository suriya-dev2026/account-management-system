package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberCategoryRequest {

    @NotBlank(message = "category field is required")
    @ValidInput(message = "Input contains invalid characters")
    private String category;

    @ValidInput(message = "Input contains invalid characters")
    private String description;

    public void sanitizeInput() {
        setCategory(Apputility.sanitizeInput(getCategory()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
