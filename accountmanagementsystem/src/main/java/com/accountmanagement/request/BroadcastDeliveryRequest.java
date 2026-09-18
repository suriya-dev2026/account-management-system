package com.accountmanagement.request;

import java.time.LocalDate;
import java.util.UUID;
import com.accountmanagement.enums.DeliveryStatus;
import com.accountmanagement.validations.ValidUserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BroadcastDeliveryRequest {

    @NotNull(message = "Broadcast Id Is Required")
    private UUID broadcastId;

    @ValidUserId(message = "User Id Does Not Exists")
    @NotNull(message = "User Id Is Required")
    private UUID userId;

    private DeliveryStatus deliveryStatus;

    @NotBlank(message = "Sent To Is Required")
    private String sentTo;

    private LocalDate sentAt;

}
