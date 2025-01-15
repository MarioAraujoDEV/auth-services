package com.authservice.auth_service.exception;

public class EmptyFieldException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Field cannot be empty";

    public EmptyFieldException(String fieldName) {
        super(DEFAULT_MESSAGE + ": " + fieldName);
    }

    public EmptyFieldException(String fieldName, String message) {
        super(message + ": " + fieldName);
    }
}