package com.accountmanagement.request;

import com.accountmanagement.enums.AttendanceStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceUpdateRequest {

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
