package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class AlreadyExistingEmailException extends ServiceException {
    public AlreadyExistingEmailException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
