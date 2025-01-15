package com.authservice.auth_service.exception;

public class BadCredentialsException extends RuntimeException  {
    private static final String DEFAULT_MESSAGE = "Invalid email or password";

    public BadCredentialsException() {
        super(DEFAULT_MESSAGE);
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
