package com.example.schedule.user.repository;

import com.example.schedule.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(
            @NotBlank(message = "회원가입 시 입력한 이메일을 입력해주세요.")
            @Email(message = "올바른 형식의 이메일을 입력해주세요.") String email
    );
}
