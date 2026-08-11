package com.accountmanagement.request;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionFeatureRequest {

    @NotBlank(message = "subscription feature is required")
    @Size(min = AppConstants.minNameLength, max = AppConstants.maxNameLength, message = "subscription feature must be between "
            + AppConstants.minNameLength + " and " + AppConstants.maxNameLength + " characters")
    @ValidInput(message = "Input contains invalid characters")
    private String name;

    @ValidInput(message = "Input contains invalid characters")
    private String description;

    public void sanitizeInput() {
        setName(Apputility.sanitizeInput(getName()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
