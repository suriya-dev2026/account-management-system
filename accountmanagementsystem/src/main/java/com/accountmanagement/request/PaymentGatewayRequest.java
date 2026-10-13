package com.accountmanagement.request;

import com.accountmanagement.enums.GatewayStatus;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentGatewayRequest {

    @Size(max = 100, message = "Gateway Reference must not exceed 100 characters.")
    private String gatewayReference;

    private JsonNode responseJson;

    private GatewayStatus status;

}
