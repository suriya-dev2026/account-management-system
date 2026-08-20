package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.validations.ValidDate;
import com.accountmanagement.validations.ValidOrganizationId;
import com.accountmanagement.validations.ValidStudentId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SundaySchoolAttendanceUpdateRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @ValidStudentId(message = "Student Id Does Not Exists")
    @NotNull(message = "Student Id Is Required")
    private UUID studentId;

    @ValidDate(message = "pleases enter a valid date birth date cannot be in the future")
    private LocalDate attendanceDate;

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
