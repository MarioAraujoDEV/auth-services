package com.zenfit.authservice.exception;

public class TokenExpiredException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "The refresh token has expired";

    public TokenExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public TokenExpiredException(String message) {
        super(message);
    }

}
