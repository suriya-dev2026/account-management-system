package com.accountmanagement.controller.v1;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.BroadcastMessage;
import com.accountmanagement.model.Broadcast;
import com.accountmanagement.request.BroadcastRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.BroadcastService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/broadcast")
@Tag(name = "BroadcastController")
public class BroadcastController {

    private final BroadcastService broadcastService;

    public BroadcastController(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBroadcast(@Valid @RequestBody BroadcastRequest broadcastRequest) {
        broadcastRequest.sanitizeInput();
        broadcastService.createBroadcast(broadcastRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.CREATE_BROADCAST, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBroadcast(@PathVariable UUID id,
            @Valid @RequestBody BroadcastRequest broadcastRequest) {
        broadcastRequest.sanitizeInput();
        broadcastService.updateBroadcast(id, broadcastRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.UPDATE_BROADCAST, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBroadcastById(@PathVariable UUID id) {
        broadcastService.deleteBroadcastById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.DELETE_BROADCAST, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllBroadcast() {
        List<Broadcast> broadcast = broadcastService.viewAllBroadcasts();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BroadcastMessage.VIEW_ALL_BROADCAST, 200);
        response.setData(broadcast);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
