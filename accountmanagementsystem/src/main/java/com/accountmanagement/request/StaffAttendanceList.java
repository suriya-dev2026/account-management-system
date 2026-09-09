package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffAttendanceList {

    @NotNull(message = "Staff Id Is Required")
    private UUID staffId;

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
