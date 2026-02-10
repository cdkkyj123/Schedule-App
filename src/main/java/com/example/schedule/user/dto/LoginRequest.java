package com.example.schedule.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "회원가입 시 입력한 이메일을 입력해주세요.")
    @Email(message = "올바른 형식의 이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "회원가입 시 입력한 비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;
}
