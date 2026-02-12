package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class UnmatchPasswordException extends ServiceException {
    public UnmatchPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
