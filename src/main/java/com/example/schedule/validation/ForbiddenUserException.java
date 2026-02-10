package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class ForbiddenUserException extends ServiceException {
    public ForbiddenUserException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
