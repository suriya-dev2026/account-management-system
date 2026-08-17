package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccessControlRouteRequest {

    @ValidInput(message = "Controller Name Contains Invalid Characters")
    @NotBlank(message = "Controller Name Cannot Be Blank")
    private String controllerName;

    @ValidInput(message = "Backend Route Contains Invalid Characters")
    @NotBlank(message = "Backend Route Cannot Be Blank")
    private String backendRoute;

    @ValidInput(message = "Frontend Route Contains Invalid Characters")
    private String frontendRoute;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    private Integer isDefault;

    public void sanitizeInput() {
        setControllerName(Apputility.sanitizeInput(getControllerName()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
