package com.io.cardtransactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: %s",
                entityName, fieldName, fieldValue));
    }

    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
