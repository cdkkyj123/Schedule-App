package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class CommentFullException extends ServiceException {
    public CommentFullException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
