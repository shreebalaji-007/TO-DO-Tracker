package com.todotracker.todoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TodoAlreadyExistsException extends RuntimeException {
    public TodoAlreadyExistsException(String message) {
        super(message);
    }
}
