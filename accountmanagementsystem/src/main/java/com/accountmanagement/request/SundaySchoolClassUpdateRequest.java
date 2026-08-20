package com.accountmanagement.request;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolClassUpdateRequest {

    @NotBlank(message = "Class Name Is Required")
    @ValidInput(message = "Class Name Contains Invalid Characters")
    private String className;

    @NotNull(message = "Class Number Is Required")
    private Integer classNumber;

    public void sanitizeInput() {
        setClassName(Apputility.sanitizeInput(getClassName()));
    }
}
