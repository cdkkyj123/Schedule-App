package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleRequest {
    private String title;
    private String poster;
    private String password;
}
