package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationRequest {

    @NotBlank(message = "area field is required")
    private String area;

    private String description;

    public void sanitizeInput() {
        setArea(Apputility.sanitizeInput(getArea()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }
}
