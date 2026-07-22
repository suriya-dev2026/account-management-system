package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationRequest {

    @NotBlank(message = "location field is required")
    private String location;

    private String description;

    public void sanitizeInput() {
        setLocation(Apputility.sanitizeInput(getLocation()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }
}
