package com.example.schedule.comment.entity;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.schedule.common.AuthConstants.LOGIN_USER;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

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
