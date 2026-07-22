package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberCategoryRequest {

    @NotBlank(message = "category field is required")
    private String category;

    private String description;

    public void sanitizeInput() {
        setCategory(Apputility.sanitizeInput(getCategory()));
    }

}
