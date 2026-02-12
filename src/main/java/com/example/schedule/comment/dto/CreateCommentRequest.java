package com.example.schedule.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotBlank(message = "내용은 공백일 수 없습니다.")
    @Size(max = 100, message = "내용이 100자를 초과할 수 없습니다.")
    private String content;
}
