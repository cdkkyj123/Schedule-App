package com.example.schedule.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentRequest {
    private Long scheduleId;
    private String content;
    private String commenter;
    private String password;
}
