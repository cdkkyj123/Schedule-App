package com.example.schedule.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    @NotBlank(message = "제목은 공백일 수 없습니다.")
    @Size(max = 20, message = "제목은 20글자를 넘어갈 수 없습니다.")
    private String title;
    @Size(max = 200, message = "내용은 200글자를 초과할 수 없습니다.")
    private String content;
}
