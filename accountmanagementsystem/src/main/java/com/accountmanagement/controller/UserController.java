package com.accountmanagement.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRegistrationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.UserService;
import jakarta.validation.Valid;

@RestController
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        loginRequest.sanitizeInput();
        userService.loginUser(loginRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        Map<String, String> tokenResponse = userService.verifyLoginOtp(verifyOtpRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.OTP_VERIFY, 200);
        response.setRequestInfo(tokenResponse);
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

        List<UserDto> user = userService.getAllUsers();
        if (user == null || user.isEmpty()) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, UserMessage.USER_NOT_FOUND, 404);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
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
        userService.verifyResetOtp(verifyOtpRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.OTP_VERIFY, 200);
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
