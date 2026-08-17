package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.validations.ValidModulePresetId;
import com.accountmanagement.validations.ValidRoleId;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccessControlRolePresetAccessRequest {

    @NotNull(message = "Role Id is Required")
    @ValidRoleId(message = "Role Id does not exists")
    private UUID roleId;

    @NotNull(message = "Module Preset Id is Required")
    @ValidModulePresetId(message = "Module Preset Id does not exists")
    private Integer modulePresetId;

}
