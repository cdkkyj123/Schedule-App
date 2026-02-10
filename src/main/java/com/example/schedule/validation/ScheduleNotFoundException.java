package com.example.schedule.validation;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends ServiceException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
