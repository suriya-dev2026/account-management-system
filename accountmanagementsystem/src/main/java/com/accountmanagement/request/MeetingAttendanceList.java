package com.accountmanagement.request;

import java.util.UUID;
import com.accountmanagement.enums.AttendanceStatus;
import com.accountmanagement.validations.ValidMemberId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeetingAttendanceList {

    @ValidMemberId(message = "Member Id Is Required")
    @NotNull(message = "Member Id Is Required")
    private UUID memberId;

    @NotNull(message = "Attendance Status Is Required")
    private AttendanceStatus attendanceStatus;

}
