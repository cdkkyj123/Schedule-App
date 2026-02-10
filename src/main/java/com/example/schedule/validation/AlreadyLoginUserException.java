package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class AlreadyLoginUserException extends ServiceException{
    public AlreadyLoginUserException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
