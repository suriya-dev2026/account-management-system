package com.accountmanagement.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String userNameOrEmail;

    private String password;

}
