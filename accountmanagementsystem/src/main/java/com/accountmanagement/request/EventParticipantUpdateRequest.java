package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventParticipantUpdateRequest {

    @NotNull(message = "Event Id Is Required")
    private UUID eventId;

    @NotBlank(message = "Contact Number Is Required")
    @ValidInput(message = "Contact Number Contains Invalid Characters")
    private String contactNumber;

    @ValidInput(message = "Emergency Contact Name Contains Invalid Characters")
    private String emergencyContactName;

    @ValidInput(message = "Emergency Contact Number Contains Invalid Characters")
    private String emergencyContactNumber;

    public void sanitizeInput() {
        setContactNumber(Apputility.sanitizeInput(getContactNumber()));
        setEmergencyContactName(Apputility.sanitizeInput(getEmergencyContactName()));
        setEmergencyContactNumber(Apputility.sanitizeInput(getEmergencyContactNumber()));
    }
}
