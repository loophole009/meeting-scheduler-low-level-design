package com.oracle.ischeduler.exception;

public class NoInterviewerAvailableException extends RuntimeException {
    public NoInterviewerAvailableException(String message) {
        super(message);
    }
}