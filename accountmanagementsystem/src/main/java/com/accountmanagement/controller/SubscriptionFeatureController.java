package com.accountmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionFeature;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionFeatureService;

@RestController
@RequestMapping("subscription/feature")
public class SubscriptionFeatureController {

    private final SubscriptionFeatureService subscriptionFeatureService;

    public SubscriptionFeatureController(SubscriptionFeatureService subscriptionFeatureService) {
        this.subscriptionFeatureService = subscriptionFeatureService;
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
