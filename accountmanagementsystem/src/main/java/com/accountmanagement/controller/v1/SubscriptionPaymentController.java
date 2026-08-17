package com.accountmanagement.controller.v1;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/subscription/payment")
@Tag(name = "SubscriptionPaymentController")
public class SubscriptionPaymentController {

    private final SubscriptionPaymentService subscriptionPaymentService;

    public SubscriptionPaymentController(SubscriptionPaymentService subscriptionPaymentService) {
        this.subscriptionPaymentService = subscriptionPaymentService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllSubscriptionPayment() {
        List<SubscriptionPayment> subscriptionPayment = subscriptionPaymentService.viewAllSubscriptionPayment();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.VIEW_ALL_SUBSCRIPTION_PAYMENT,
                200);
        response.setData(subscriptionPayment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
