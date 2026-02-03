package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String content;
    private String commenter;
    private String password;
    private Long scheduleId; // 댓글의 원본 일정의 아이디를 저장할 필드

    public Comment(String content, String commenter, String password, Long scheduleId) {
        this.content = content;
        this.commenter = commenter;
        this.password = password;
        this.scheduleId = scheduleId;
    }

    public void updateComment(String content, String commenter) {
        this.content = content;
        this.commenter = commenter;
    }
}
