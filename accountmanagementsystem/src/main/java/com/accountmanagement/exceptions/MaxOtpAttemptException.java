package com.accountmanagement.exceptions;

public class MaxOtpAttemptException extends RuntimeException {

    public MaxOtpAttemptException(String message) {
        super(message);
    }
}
