package com.accountmanagement.exceptions;

public class AccountLockException extends RuntimeException {

    public AccountLockException(String message) {
        super(message);
    }
}
