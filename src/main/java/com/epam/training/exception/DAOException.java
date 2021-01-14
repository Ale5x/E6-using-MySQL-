package com.epam.training.exception;

public class DAOException extends Exception {

    private static final long serialVersionUID = 5340859232861460811L;

    public DAOException() {
        super();
    }

    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }

    public DAOException(Throwable exception) {
        super(exception);
    }

    public DAOException(String message) {
        super(message);
    }
}
