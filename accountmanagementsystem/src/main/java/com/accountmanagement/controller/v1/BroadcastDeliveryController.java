package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.BroadcastMessage;
import com.accountmanagement.model.BroadcastDelivery;
import com.accountmanagement.request.BroadcastDeliveryRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.BroadcastDeliveryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/broadcast/delivery")
public class BroadcastDeliveryController {

    private final BroadcastDeliveryService broadcastDeliveryService;

    public BroadcastDeliveryController(BroadcastDeliveryService broadcastDeliveryService) {
        this.broadcastDeliveryService = broadcastDeliveryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBroadcastDelivery(
            @Valid @RequestBody BroadcastDeliveryRequest broadcastDeliveryRequest) {
        broadcastDeliveryService.createBroadcastDelivery(broadcastDeliveryRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.CREATE_BROADCAST_DELIVERY, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllBroadcastDelivery() {
        List<BroadcastDelivery> broadcastDelivery = broadcastDeliveryService.viewAllBroadcastDelivery();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.VIEW_ALL_BROADCAST_DELIVERY, 200);
        response.setData(broadcastDelivery);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
