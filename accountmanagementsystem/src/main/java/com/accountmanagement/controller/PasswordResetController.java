package com.accountmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.messages.UserMessage;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.PasswordResetService;
import jakarta.validation.Valid;

@RestController
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot/password/{email}")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @PathVariable String email) {
        try {
            passwordResetService.forgotPassword(email);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                    UserMessage.OTP, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR,
                    e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify/reset/otp")
    public ResponseEntity<ApiResponse> verifyResetOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        try {
            passwordResetService.verifyResetOtp(verifyOtpRequest);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                    UserMessage.OTP_VERIFY, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR,
                    e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change/password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        try {

            passwordResetService.changePassword(changePasswordRequest);
            ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                    UserMessage.CHANGE_PASSWORD, 200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(AppConstants.ERROR,
                    e.getMessage(), 500);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
