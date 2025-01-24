package com.zenfit.authservice.exception;

public class TokenGenerationException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Failed to generate token";

    public TokenGenerationException() {
        super(DEFAULT_MESSAGE);
    }

    public TokenGenerationException(String message) {
        super(message);
    }

}
