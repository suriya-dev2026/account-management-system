package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.validations.ValidVbsStudentId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VbsAttendanceList {

    @ValidVbsStudentId(message = "Vbs Student Id Does Not Exists")
    @NotNull(message = "Vbs Student Id Is Required")
    private UUID studentId;

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
