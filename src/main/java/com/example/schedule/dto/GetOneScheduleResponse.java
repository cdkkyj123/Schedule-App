package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetOneScheduleResponse {
    private final Long id;
    private final String title;
    private final String contents;
    private final String poster;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetCommentResponse> comments;

    public GetOneScheduleResponse(Long id, String title, String contents, String poster, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.poster = poster;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }
}
