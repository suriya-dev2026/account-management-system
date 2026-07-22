package com.accountmanagement.exceptions;

public class InvalidRefreshKeyException extends RuntimeException {
    public InvalidRefreshKeyException(String message) {
        super(message);
    }
}
