package com.accountmanagement.controller.v1;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.constants.message.SubscriptionMessage;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.model.SubscriptionPayment;
import com.accountmanagement.request.ChangeTemporaryPasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.OrganizationRegistrationRequest;
import com.accountmanagement.request.PaymentSuccessRequest;
import com.accountmanagement.request.ResendEmailVerificationRequest;
import com.accountmanagement.request.SubscriptionOrganizationRequest;
import com.accountmanagement.request.SubscriptionPaymentRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.OrganizationService;
import com.accountmanagement.service.SubscriptionOrganizationService;
import com.accountmanagement.service.SubscriptionPaymentService;
import com.accountmanagement.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "auth/v1")
@Tag(name = "AuthController")
public class AuthController {

    private final OrganizationService organizationService;

    private final UserService userService;

    private final SubscriptionOrganizationService subscriptionOrganizationService;

    private final SubscriptionPaymentService subscriptionPaymentService;

    public AuthController(OrganizationService organizationService, UserService userService,
            SubscriptionOrganizationService subscriptionOrganizationService,
            SubscriptionPaymentService subscriptionPaymentService) {
        this.organizationService = organizationService;
        this.userService = userService;
        this.subscriptionOrganizationService = subscriptionOrganizationService;
        this.subscriptionPaymentService = subscriptionPaymentService;
    }

    @PostMapping("/organization/register")
    public ResponseEntity<ApiResponse> createOrganization(
            @Valid @RequestBody OrganizationRegistrationRequest organizationRequest) {
        organizationRequest.sanitizeInput();
        organizationService.registerOrganization(organizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, OrganizationMessage.ADD_ORGANIZATION, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegistrationRequest userRequest) {
        userRequest.sanitizeInput();
        userService.registerUser(userRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_REGISTER, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user/verify/email/otp")
    public ResponseEntity<ApiResponse> verifyEmail(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        userService.verifyEmailOtp(verifyOtpRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_EMAIL_VERIFY, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/resend/verification/otp")
    public ResponseEntity<ApiResponse> verifyEmail(
            @Valid @RequestBody ResendEmailVerificationRequest resendEmailVerificationRequest) {
        userService.sendOtpForEmailVerification(resendEmailVerificationRequest.getOrganizationId(),
                resendEmailVerificationRequest.getEmail());
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP_RESEND, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/subscription/organization/add")
    public ResponseEntity<ApiResponse> createSubscriptionOrganization(
            @Valid @RequestBody SubscriptionOrganizationRequest subscriptionOrganizationRequest) {
        subscriptionOrganizationService.createSubscription(subscriptionOrganizationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                SubscriptionMessage.CREATE_SUBSCRIPTION_ORGANIZATION, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/subscription/payment/add")
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

    @PostMapping("/subscription/payment/webhook")
    public ResponseEntity<ApiResponse> webhook(@RequestBody PaymentSuccessRequest request) {
        subscriptionPaymentService.paymentSuccess(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, SubscriptionMessage.SUBSCRIPTION_PAYMENT_MESSAGE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resend/temporary/password/{userId}")
    public ResponseEntity<ApiResponse> resendCredential(@PathVariable UUID userId) {
        userService.resendTemporaryCredentials(userId);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.RESEND_CREDENTIAL, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        loginRequest.sanitizeInput();
        userService.loginUser(loginRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/user/verify/otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        ApiResponse response = userService.verifyLoginOtp(verifyOtpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/user/change/temporary/password")
    public ResponseEntity<ApiResponse> changeTemporaryPassword(
            @Valid @RequestBody ChangeTemporaryPasswordRequest request) {
        userService.changeTemporaryPassword(request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.TEMPORARY_PASSWORD_CHANGED,
                200);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user/signout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String authHeader) {
        userService.signout(authHeader);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.LOGOUT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
