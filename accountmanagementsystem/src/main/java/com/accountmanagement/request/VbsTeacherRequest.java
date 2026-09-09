package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.enums.Gender;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidMemberId;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidVbsClassId;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VbsTeacherRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidMemberId(message = "Member Id Does Not Exists")
    private UUID memberId;

    @ValidVbsClassId(message = "Vbs Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private Integer classId;

    @ValidInput(message = "Teacher Name Contains Invalid Characters")
    private String teacherName;

    @ValidInput(message = "Teacher Type Contains Invalid Characters")
    private String teacherType;

    private Gender gender;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 100, message = "Age should not exceed 100")
    private Integer age;

    @ValidDate(message = "pleases enter a valid date. Birth date cannot be in the future")
    private LocalDate dateOfBirth;

    @NotNull(message = "please enter date of join")
    @ValidDate(message = "pleases enter a valid date. Join date cannot be in the future")
    private LocalDate dateOfJoin;

    @ValidInput(message = "Interest Area Contains Invalid Characters")
    private String interestArea;

    private String priority;

    public void sanitizeInput() {
        setTeacherName(Apputility.sanitizeInput(getTeacherName()));
        setTeacherType(Apputility.sanitizeInput(getTeacherType()));
        setInterestArea(Apputility.sanitizeInput(getInterestArea()));
        setPriority(Apputility.sanitizeInput(getPriority()));
    }
}
