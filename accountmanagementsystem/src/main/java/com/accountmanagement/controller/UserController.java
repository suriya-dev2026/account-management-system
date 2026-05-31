package com.accountmanagement.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.dto.UserDto;
import com.accountmanagement.messages.UserMessage;
import com.accountmanagement.request.LoginRequest;
import com.accountmanagement.request.UserRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            userService.registerUser(userRequest);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_REGISTER, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            userService.loginUser(loginRequest);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.OTP, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        try {
            Map<String, String> tokenResponse = userService.verifyLoginOtp(verifyOtpRequest.getEmail(),
                    verifyOtpRequest.getOtp());
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                    UserMessage.OTP_VERIFY, 200);
            response.setRequestInfo(tokenResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshKey/{refreshKey}")
    public ResponseEntity<ApiResponse> refreshToken(@Valid @PathVariable String refreshKey) {
        try {
            String accessToken = userService.generateAccessToken(refreshKey);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                    UserMessage.NEW_ACCESS_TOKEN, 200);
            response.setAccessToken(accessToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR,
                    e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<UserDto> user = userService.getAllUsers();
            if (user == null || user.isEmpty()) {
                ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, UserMessage.USER_NOT_FOUND, 404);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USERS_RETRIEVED, 200);
            response.setData(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        try {
            userService.logoutUser();
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.LOGOUT, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR, e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
