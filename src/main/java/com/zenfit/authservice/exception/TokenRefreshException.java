package com.zenfit.authservice.exception;

public class TokenRefreshException extends RuntimeException  {

    private static final String DEFAULT_MESSAGE = "Refresh Token is expired or invalid";

    public TokenRefreshException() {
        super(DEFAULT_MESSAGE);
    }

    public TokenRefreshException(String message) {
        super(message);
    }
}
