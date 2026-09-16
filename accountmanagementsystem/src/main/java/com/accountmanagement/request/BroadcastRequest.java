package com.accountmanagement.request;

import java.util.UUID;

import com.accountmanagement.utility.Apputility;
import com.accountmanagement.validations.ValidInput;
import com.accountmanagement.validations.ValidOrganizationId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BroadcastRequest {

    @ValidOrganizationId(message = "Organization Id Does Not Exists")
    @NotNull(message = "Organization Id Is Required")
    private UUID organizationId;

    @NotBlank(message = "Broadcast Type Is Required")
    @ValidInput(message = "Broadcast Message Contains Invalid Character")
    private String broadcastType;

    @NotBlank(message = "Message Is Required")
    @ValidInput(message = "Message Contains Invalid Characters")
    private String message;

    public void sanitizeInput() {
        setBroadcastType(Apputility.sanitizeInput(getBroadcastType()));
        setMessage(Apputility.sanitizeInput(getMessage()));
    }
}
