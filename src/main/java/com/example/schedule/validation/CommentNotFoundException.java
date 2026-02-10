package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends ServiceException {
    public CommentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
