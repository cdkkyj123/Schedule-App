package com.example.schedule.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleRequest {
    private String title;
    private String content;
}
