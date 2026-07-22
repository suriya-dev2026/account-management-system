package com.accountmanagement.exceptions;

public class RefreshKeyExpiredException extends RuntimeException {
    public RefreshKeyExpiredException(String message) {
        super(message);
    }

}
