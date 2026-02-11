package com.example.schedule.schedule.dto;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetPageResponse {
    private final String title;
    private final String content;
    private final int commentsCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String userName;

    public GetPageResponse(Schedule schedule, int commentsCount) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentsCount = commentsCount;
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.userName = schedule.getUser().getName();
    }
}
