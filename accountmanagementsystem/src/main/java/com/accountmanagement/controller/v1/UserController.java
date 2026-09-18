package com.accountmanagement.controller.v1;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.UserMessage;
import com.accountmanagement.request.ChangePasswordRequest;
import com.accountmanagement.request.ForgotPasswordRequest;
import com.accountmanagement.request.UserUpdationRequest;
import com.accountmanagement.request.VerifyOtpRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.PasswordResetService;
import com.accountmanagement.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/user")
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    private final PasswordResetService passwordResetService;

    UserController(UserService userService, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable UUID id,
            @Valid @RequestBody UserUpdationRequest userUpdationRequest) {
        userUpdationRequest.sanitizeInput();
        userService.updateUser(id, userUpdationRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_UPDATED, 200);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.USER_DELETED, 200);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/refreshKey/{refreshKey}")
    public ResponseEntity<ApiResponse> refreshToken(@Valid @PathVariable String refreshKey) {
        String accessToken = userService.generateAccessToken(refreshKey);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, UserMessage.NEW_ACCESS_TOKEN, 200);
        response.setAccessToken(accessToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<ApiResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        passwordResetService.forgotPassword(forgotPasswordRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.OTP_SENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verify/reset/otp")
    public ResponseEntity<ApiResponse> verifyResetOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        verifyOtpRequest.sanitizeInput();
        ApiResponse response = passwordResetService.verifyResetOtp(verifyOtpRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/change/password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        changePasswordRequest.sanitizeInput();
        passwordResetService.changePassword(changePasswordRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                UserMessage.CHANGE_PASSWORD, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
