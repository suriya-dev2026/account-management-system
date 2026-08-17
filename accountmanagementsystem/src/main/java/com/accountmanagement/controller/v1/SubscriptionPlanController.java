package com.accountmanagement.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionPlan;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionPlanService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/ubscription/plan")
@Tag(name = "SubscriptionPlanController")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAll() {
        List<SubscriptionPlan> subscriptionPlan = subscriptionPlanService.viewAllSubscriptionPlan();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.VIEW_ALL_SUBCRIPTION_PLAN,
                200);
        response.setData(subscriptionPlan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
