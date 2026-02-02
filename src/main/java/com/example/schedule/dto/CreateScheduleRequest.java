package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleRequest {
    private String title;
    private String contents;
    private String poster;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
