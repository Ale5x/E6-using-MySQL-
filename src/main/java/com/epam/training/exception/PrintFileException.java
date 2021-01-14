package com.epam.training.exception;

public class PrintFileException extends Exception {

    public PrintFileException() {
    }

    public PrintFileException(String message) {
        super(message);
    }

    public PrintFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrintFileException(Throwable cause) {
        super(cause);
    }
}
