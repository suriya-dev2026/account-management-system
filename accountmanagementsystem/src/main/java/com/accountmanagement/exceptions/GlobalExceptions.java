package com.accountmanagement.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.accountmanagement.response.ValidationErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() == UUID.class) {
            return ResponseEntity.badRequest()
                    .body("Unrecognized ID, please enter valid id.");
        }
        return ResponseEntity.badRequest().body("Invalid request.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Map<String, Object> error = new HashMap<>();
        String message = "Invalid Json Format";
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (UUID.class.equals(invalidFormatException.getTargetType())) {
                message = "Unrecognized ID, please enter valid id.";
            } else if (Integer.class.equals(invalidFormatException.getTargetType())) {
                message = "Invalid value. Please provide a valid integer.";
            }
        }
        if ("Invalid Json Format".equals(message)
                && ex.getMessage() != null) {
            String exceptionMessage = ex.getMessage();
            if (ex.getMessage().contains("Gender")) {
                message = "Invalid gender value. Allowed values are Male or Female";
            } else if (exceptionMessage.contains("BillingCycle")) {
                message = "Invalid billing cycle value. Allowed values are MONTHLY,YEARLY";
            } else if (exceptionMessage.contains("PaymentStatus")) {
                message = "Invalid Payment status value. Allowed values are PENDING, SUCCESS,ALLOWED, CANCELLED";
            } else if (exceptionMessage.contains("SubscriptionOrganizationStatus")) {
                message = "Invalid status value. Allowed values arePENDING,ACTIVE,EXPIRED,CANCELLED";
            } else if (exceptionMessage.contains("Integer")) {
                message = "Invalid value. Please provide a valid integer.";
            } else if (exceptionMessage.contains("billingCycle")) {
                message = "Invalid billing Cycle value. Allowed only MONTHLY and YEARLY";
            }
        }
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        error.put("message", message);
        error.put("error", "Bad Request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRecordNotFoundException(RecordNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 404);
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialException(InvalidCredentialsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 401);
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 400);
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        ex.printStackTrace();
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 500);
        error.put("message", "Internal Server Error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", 409);
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
