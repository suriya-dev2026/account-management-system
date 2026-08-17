package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionOrganization;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionOrganizationService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/subscription/organization")
@Tag(name = "SubcriptionOrganizationController")
public class SubscriptionOrganizationController {

    private final SubscriptionOrganizationService subscriptionOrganizationService;

    public SubscriptionOrganizationController(SubscriptionOrganizationService subscriptionOrganizationService) {
        this.subscriptionOrganizationService = subscriptionOrganizationService;
    }

    // @PostMapping("/add")
    // public ResponseEntity<ApiResponse> createSubscriptionOrganization(
    // @Valid @RequestBody SubscriptionOrganizationRequest
    // subscriptionOrganizationRequest) {
    // subscriptionOrganizationService.createSubscription(subscriptionOrganizationRequest);
    // ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
    // SubscriptionMessage.CREATE_SUBSCRIPTION_ORGANIZATION, 200);
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllSubscriptionOrganization() {
        List<SubscriptionOrganization> subscriptionOrganization = subscriptionOrganizationService
                .viewAllSubscriptionOrganization();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SubscriptionMessage.CREATE_SUBSCRIPTION_ORGANIZATION, 200);
        response.setData(subscriptionOrganization);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
