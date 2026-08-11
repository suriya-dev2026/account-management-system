package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationRequest {

    @NotBlank(message = "location field is required")
    @ValidInput(message = "Input contains invalid characters")
    private String location;

    @ValidInput(message = "Input contains invalid characters")
    private String description;

    public void sanitizeInput() {
        setLocation(Apputility.sanitizeInput(getLocation()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }
}
