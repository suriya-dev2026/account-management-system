package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.validations.ValidClassId;
import com.accountmanagement.validations.ValidDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolTeacherUpdateRequest {

    @ValidClassId(message = "Class Id Does Not Exists")
    @NotNull(message = "Class Id Is Required")
    private UUID classId;

    @ValidDate(message = "Invalid Date Format")
    @NotNull(message = "Date OF Join Is Required")
    private LocalDate dateOfJoin;
}
