package com.accountmanagement.exceptions;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public ResponseEntity<ValidationErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("422", "Validation Error");
        if (UUID.class.equals(ex.getRequiredType())) {
            validationErrorResponse.getErrorList().put(ex.getName(), "Unrecognized ID, please enter valid id.");
        } else {
            validationErrorResponse.getErrorList().put(ex.getName(), "Invalid Request");
        }
        return ResponseEntity.unprocessableEntity().body(validationErrorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
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
                message = "Invalid Payment status value. Allowed values are PENDING, SUCCESS,CANCELLED";
            } else if (exceptionMessage.contains("SubscriptionOrganizationStatus")) {
                message = "Invalid status value. Allowed values arePENDING,ACTIVE,EXPIRED,CANCELLED";
            } else if (exceptionMessage.contains("Integer")) {
                message = "Invalid value. Please provide a valid integer.";
            } else if (exceptionMessage.contains("billingCycle")) {
                message = "Invalid billing Cycle value. Allowed only MONTHLY and YEARLY";
            }
        }
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("422", "Validation Error");
        validationErrorResponse.getErrorList().put("request", message);
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ValidationErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("404",
                "Resource not found");
        validationErrorResponse.getErrorList().put("error", ex.getMessage());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ValidationErrorResponse> handleInvalidCredentialException(InvalidCredentialsException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("401",
                "Authentication Error");
        validationErrorResponse.getErrorList().put("error", ex.getMessage());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ValidationErrorResponse> handleRuntimeException(RuntimeException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("400", ex.getMessage());
        validationErrorResponse.getErrorList().put("error", ex.getMessage());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ValidationErrorResponse> handleOtpExpiredException(
            OtpExpiredException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse("400", ex.getMessage());
        response.getErrorList().put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ValidationErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("404",
                "Resource not found");
        validationErrorResponse.getErrorList().put("error", ex.getMessage());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error occured", ex);
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("500", "Internal Server Error");
        validationErrorResponse.getErrorList().put("error", "An unexpected error occured");
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ValidationErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse("409", "Resource Conflict");
        validationErrorResponse.getErrorList().put("error", ex.getMessage());
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.CONFLICT);
    }

}
