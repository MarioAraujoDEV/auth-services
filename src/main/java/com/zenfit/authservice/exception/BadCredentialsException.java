package com.zenfit.authservice.exception;

public class BadCredentialsException extends RuntimeException  {
    private static final String DEFAULT_MESSAGE = "Invalid username or password";

    public BadCredentialsException() {
        super(DEFAULT_MESSAGE);
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}