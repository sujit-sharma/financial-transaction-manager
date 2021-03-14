package com.sujit.reconciliationwebapp.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {}
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
