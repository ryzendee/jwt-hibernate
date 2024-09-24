package com.ryzend.app.exception;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String message) {
        super(message);
    }

    public UsernameExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
