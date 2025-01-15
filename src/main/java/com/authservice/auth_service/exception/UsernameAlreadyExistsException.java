package com.authservice.auth_service.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Username already exists";

    public UsernameAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}