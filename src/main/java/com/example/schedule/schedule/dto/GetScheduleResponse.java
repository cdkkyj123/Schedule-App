package com.example.schedule.schedule.dto;

import com.example.schedule.comment.dto.GetCommentResponse;
import com.example.schedule.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetScheduleResponse {
    private final Long id;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetCommentResponse> comments;

    public GetScheduleResponse(Schedule schedule, List<GetCommentResponse> comments) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
        this.comments = comments;
    }
}
