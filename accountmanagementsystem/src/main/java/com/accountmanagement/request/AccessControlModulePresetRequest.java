package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccessControlModulePresetRequest {

    @ValidInput(message = "Module Name Contains Invalid Characters")
    @NotBlank(message = "Module Name Is Required")
    private String moduleName;

    @ValidInput(message = "Preset Name Contains Invalid Characters")
    @NotBlank(message = "Preset Name Is Required")
    private String presetName;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    public void sanitizeInput() {
        setModuleName(Apputility.sanitizeInput(getModuleName()));
        setPresetName(Apputility.sanitizeInput(getPresetName()));
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
