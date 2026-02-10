package com.example.schedule.comment.dto;

import com.example.schedule.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final String content;
    private final String commenter;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.commenter = comment.getCommenter();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
