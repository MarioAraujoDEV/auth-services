package com.authservice.auth_service.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "User not found";

    // Constructor sin mensaje personalizado
    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    // Constructor con mensaje personalizado
    public UserNotFoundException(String message) {
        super(message);
    }
}