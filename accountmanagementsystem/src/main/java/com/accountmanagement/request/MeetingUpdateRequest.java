package com.accountmanagement.request;

import java.time.LocalDate;
import java.time.LocalTime;
import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidStartDate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeetingUpdateRequest {

    @NotNull(message = "Meeting Type Id Is Required")
    private Integer meetingTypeId;

    @ValidStartDate(message = "Please Enter a Valid Date")
    @NotNull(message = "Meeting Date Is Required")
    private LocalDate meetingDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ValidInput(message = "Description Contains Invalid Characters")
    private String description;

    public void sanitizeInput() {
        setDescription(Apputility.sanitizeInput(getDescription()));
    }

}
