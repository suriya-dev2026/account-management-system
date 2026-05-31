package com.accountmanagement.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private List<?> data;

    private Map<String, String> headers = new HashMap<>();

    private Object requestInfo;

    private String message;

    private String accessToken;

    private String refreshKey;

    public ApiResponse(String status, String message, Integer statusCode) {
        headers.put("status", status);
        headers.put("message", message);
        headers.put("statusCode", statusCode.toString());

    }

}
