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
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.request.SubscriptionFeatureRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionFeatureService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/subscription/feature")
@Tag(name = "SubcriptionFeatureController")
public class SubscriptionFeatureController {

    private final SubscriptionFeatureService subscriptionFeatureService;

    public SubscriptionFeatureController(SubscriptionFeatureService subscriptionFeatureService) {
        this.subscriptionFeatureService = subscriptionFeatureService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSubcriptionFeature(
            @Valid @RequestBody SubscriptionFeatureRequest subscriptionFeatureRequest) {
        subscriptionFeatureRequest.sanitizeInput();
        subscriptionFeatureService.addSubscriptionFeature(subscriptionFeatureRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.ADD_SUBSCRIPTION_FEATURE,
                201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateSubcriptionFeature(@PathVariable UUID id,
            @Valid @RequestBody SubscriptionFeatureRequest subscriptionFeatureRequest) {
        subscriptionFeatureRequest.sanitizeInput();
        subscriptionFeatureService.updateSubscriptionFeature(id, subscriptionFeatureRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.UPDATE_SUBSCRIPTION_FEATURE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSubcriptionFeatureById(@PathVariable UUID id) {
        subscriptionFeatureService.deleteSubscriptionFeatureById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.DELETE_SUBSCRIPTION_FEATURE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAll() {
        List<SubscriptionFeature> subscriptionFeature = subscriptionFeatureService.viewAllSubscriptionFeature();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.VIEW_ALL_SUBCRIPTION_FEATURE,
                200);
        response.setData(subscriptionFeature);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
