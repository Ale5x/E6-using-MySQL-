package com.epam.training.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException() {
    }

    public ConnectionException(Throwable e) {
    }
}