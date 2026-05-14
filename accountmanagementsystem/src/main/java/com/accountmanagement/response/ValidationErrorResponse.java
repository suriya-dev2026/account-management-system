package com.accountmanagement.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class ValidationErrorResponse {

    Map<String, String> errors = new HashMap<>();

    Map<String, String> errorList = new HashMap<>();

    public ValidationErrorResponse(String status, String message) {
        errors.put("status", status);
        errors.put("message", message);
    }

}
