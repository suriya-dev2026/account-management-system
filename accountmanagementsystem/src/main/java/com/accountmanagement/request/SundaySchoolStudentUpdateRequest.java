package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidClassId;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolStudentUpdateRequest {

    @ValidInput(message = "Student Name Contains Invalid Characters")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 100, message = "Age should not exceed 100")
    private Integer age;

    @ValidDate(message = "pleases enter a valid date birth date cannot be in the future")
    private LocalDate dateOfBirth;

    @ValidClassId(message = "Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private UUID classId;

    public void sanitizeInput() {
        setName(Apputility.sanitizeInput(getName()));
    }

}
