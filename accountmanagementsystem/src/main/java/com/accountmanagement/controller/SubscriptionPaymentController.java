package com.accountmanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.request.PaymentSuccessRequest;
import com.accountmanagement.request.SubscriptionPaymentRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SubscriptionPaymentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "subscription/payment")
@Tag(name = "SubscriptionPaymentController")
public class SubscriptionPaymentController {

    private final SubscriptionPaymentService subscriptionPaymentService;

    public SubscriptionPaymentController(SubscriptionPaymentService subscriptionPaymentService) {
        this.subscriptionPaymentService = subscriptionPaymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createSubscriptionPayment(
            @Valid @RequestBody SubscriptionPaymentRequest subscriptionPaymentRequest) {
        SubscriptionPayment subscriptionPayment = subscriptionPaymentService
                .createSubscriptionPayment(subscriptionPaymentRequest);
        String paymentReference = subscriptionPayment.getPaymentReference();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.CREATE_SUBSCRIPTION_PAYMENT,
                201);
        response.setRequestInfo(paymentReference);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/success")
    public ResponseEntity<ApiResponse> webhook(@RequestBody PaymentSuccessRequest request) {
        subscriptionPaymentService.paymentSuccess(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.SUBSCRIPTION_PAYMENT_MESSAGE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
