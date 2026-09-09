package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StaffAttendanceRequest {

    private UUID organizationId;

    private LocalDate attendanceDate;

    @Valid
    @NotNull(message = "Attendance Lit Is Required")
    private List<StaffAttendanceList> attendanceList;

}
