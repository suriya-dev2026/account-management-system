package com.accountmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionPlanFeature;
import com.accountmanagement.request.SubscriptionPlanFeatureRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionPlanFeatureService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "subscription/plan/feature")
@Tag(name = "SubscriptionPlanFeature")
public class SubscriptionPlanFeatureController {

    private final SubscriptionPlanFeatureService subscriptionPlanFeatureService;

    public SubscriptionPlanFeatureController(SubscriptionPlanFeatureService subscriptionPlanFeatureService) {
        this.subscriptionPlanFeatureService = subscriptionPlanFeatureService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSubscriptionPlanFeature(
            @Valid @RequestBody SubscriptionPlanFeatureRequest subscriptionPlanFeatureRequest) {
        subscriptionPlanFeatureService.addSubscriptionPlanFeature(subscriptionPlanFeatureRequest);
        ApiResponse response = new ApiResponse("Success", SubscriptionMessage.ADD_SUBSCRIPTION_PLAN_FEATURE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<ApiResponse>
    // updateSubscriptionPlanFeature(@PathVariable UUID id,
    // @Valid @RequestBody SubscriptionPlanFeatureRequest
    // subscriptionPlanFeatureRequest) {
    // subscriptionPlanFeatureService.updateSubscriptionPlanFeature(id,
    // subscriptionPlanFeatureRequest);
    // ApiResponse response = new ApiResponse("Success",
    // SubscriptionMessage.UPDATE_SUBSCRIPTION_PLAN_FEATURE, 200);
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteSubscriptionPlanFeatureById(@PathVariable UUID id) {
        subscriptionPlanFeatureService.deleteSubscriptionPlanFeatureById(id);
        ApiResponse response = new ApiResponse("Success", SubscriptionMessage.DELETE_SUBSCRIPTION_PLAN_FEATURE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllSubscriptionPlanFeature() {
        List<SubscriptionPlanFeature> subscriptionPlanFeature = subscriptionPlanFeatureService
                .viewAllSubscriptionPlanFeature();
        ApiResponse response = new ApiResponse("Success", SubscriptionMessage.ADD_SUBSCRIPTION_PLAN_FEATURE, 201);
        response.setData(subscriptionPlanFeature);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
