package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;

import com.accountmanagement.validations.ValidClassId;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidStudentId;
import com.accountmanagement.validations.ValidTeacherId;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolTransitionRequest {

    @ValidStudentId(message = "Student Id Does Not Exists")
    @NotNull(message = "Student Id Is Required")
    private UUID studentId;

    @ValidClassId(message = "From Class Id Does Not Exists")
    @NotNull(message = "From Class Id Is Required")
    private UUID fromClassId;

    @ValidClassId(message = "To Class Id Does Not Exists")
    @NotNull(message = "To Class Id Is Required")
    private UUID toClassId;

    @ValidTeacherId(message = "Teacher Id Does Not Exists")
    @NotNull(message = "Teacher Id Is Required")
    private UUID transitionBy;

    @ValidDate(message = "Please Enter Valid Date")
    @NotNull(message = "Transition Date Is Required")
    private LocalDate transitionDate;

}
