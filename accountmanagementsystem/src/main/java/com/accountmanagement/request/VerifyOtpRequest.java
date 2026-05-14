package com.accountmanagement.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {

    private String email;

    private String otp;

}
