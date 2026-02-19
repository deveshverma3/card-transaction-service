package com.io.cardtransactions.exception;

public class DataIntegrityViolationException extends RuntimeException {

    public DataIntegrityViolationException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: %s",
                entityName, fieldName, fieldValue));
    }

    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
