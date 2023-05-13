package com.oracle.ischeduler.exception;

public class InvalidTimeException extends RuntimeException {
    public InvalidTimeException(final String message) {
        super(message);
    }
}