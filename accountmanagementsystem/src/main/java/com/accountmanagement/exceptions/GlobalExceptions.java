package com.accountmanagement.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.accountmanagement.response.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        Map<String, String> errorList = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errorList.put(error.getField(), error.getDefaultMessage()));
        ValidationErrorResponse errorResponse = new ValidationErrorResponse("error", ex.getMessage());
        errors.put("error", "Validation Error");
        errors.put("statusCode", "422");
        errorResponse.setErrors(errors);
        errorResponse.setErrorList(errorList);
        return new ResponseEntity<ValidationErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        error.put("message", "Invalid Json Format");
        error.put("error", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
