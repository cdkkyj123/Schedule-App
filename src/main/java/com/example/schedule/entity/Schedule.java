package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String contents;
    @Column(nullable = false)
    private String poster;
    @Column(nullable = false)
    private String password;

    public Schedule(String title, String contents, String poster, String password) {
        this.title = title;
        this.contents = contents;
        this.poster = poster;
        this.password = password;
    }

    public void update(String title, String poster) {
        this.title = title;
        this.poster = poster;
    }
}
