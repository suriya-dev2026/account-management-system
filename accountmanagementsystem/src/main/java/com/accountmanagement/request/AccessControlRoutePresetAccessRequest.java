package com.accountmanagement.request;

import com.accountmanagement.validations.ValidModulePresetId;
import com.accountmanagement.validations.ValidRouteId;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessControlRoutePresetAccessRequest {

    @NotNull(message = "Module Preset Id Is Required")
    @ValidModulePresetId(message = "Module Preset Id does not exists")
    private Integer modulePresetId;

    @NotNull(message = "Route Id Is Required")
    @ValidRouteId(message = "Route Id does not exists")
    private Integer routeId;

}
