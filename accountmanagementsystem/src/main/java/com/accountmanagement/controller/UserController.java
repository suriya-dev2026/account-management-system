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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.model.User;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.UserUpdationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegistrationRequest userRequest) {
        userRequest.sanitizeInput();
        userService.registerUser(userRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_REGISTER, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/email/verification/otp/{email}")
    public ResponseEntity<ApiResponse> verifyEmail(@PathVariable String email) {
        userService.sendOtpForEmailVerification(email);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify/email/otp")
    public ResponseEntity<ApiResponse> verifyEmail(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        userService.verifyEmailOtp(verifyOtpRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_EMAIL_VERIFY, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable UUID id,
            @Valid @RequestBody UserUpdationRequest userUpdationRequest) {
        userUpdationRequest.sanitizeInput();
        userService.updateUser(null, userUpdationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_REGISTER, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // @PostMapping("/login")
    // public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest
    // loginRequest) {
    // loginRequest.sanitizeInput();
    // userService.loginUser(loginRequest);
    // ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP,
    // 200);
    // return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    @PostMapping("/verify/otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        ApiResponse response = userService.verifyLoginOtp(verifyOtpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refreshKey/{refreshKey}")
    public ResponseEntity<ApiResponse> refreshToken(@Valid @PathVariable String refreshKey) {
        String accessToken = userService.generateAccessToken(refreshKey);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.NEW_ACCESS_TOKEN, 200);
        response.setAccessToken(accessToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> user = userService.getAllUsers();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USERS_RETRIEVED, 200);
        response.setData(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse> logout(@RequestHeader("Authorization") String authHeader) {
        userService.signout(authHeader);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.LOGOUT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot/password/{email}")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @PathVariable String email) {
        userService.forgotPassword(email);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.OTP, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify/reset/otp")
    public ResponseEntity<ApiResponse> verifyResetOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        ApiResponse response = userService.verifyResetOtp(verifyOtpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change/password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        changePasswordRequest.sanitizeInput();
        userService.changePassword(changePasswordRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.CHANGE_PASSWORD, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
