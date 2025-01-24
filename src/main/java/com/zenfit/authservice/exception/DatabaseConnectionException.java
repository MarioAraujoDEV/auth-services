package com.zenfit.authservice.exception;

public class DatabaseConnectionException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Error connecting to the database";

    public DatabaseConnectionException() {
        super(DEFAULT_MESSAGE);
    }

    public DatabaseConnectionException(String message) {
        super(message);
    }
}