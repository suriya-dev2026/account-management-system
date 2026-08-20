package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidClassId;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidTeacherId;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolStudentRequest {

    @NotNull(message = "Organization Id Is Required")
    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    private UUID organizationId;

    @NotNull(message = "Member Id Is Required")
    @ValidMemberId(message = "Membser Id Does Not Exists")
    private UUID memberId;

    @ValidInput(message = "Student Name Contains Invalid Characters")
    private String name;

    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 100, message = "Age should not exceed 100")
    private Integer age;

    @ValidDate(message = "pleases enter a valid date birth date cannot be in the future")
    private LocalDate dateOfBirth;

    @ValidClassId(message = "Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private UUID classId;

    @ValidTeacherId(message = "Sunday School Teacher Id Does Not Exists")
    private UUID teacherId;

    public void sanitizeInput() {
        setName(Apputility.sanitizeInput(getName()));
    }

}
