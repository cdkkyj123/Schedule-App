package com.example.schedule.repository;

import com.example.schedule.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JPA 쿼리 메서드
    int countByScheduleId(Long scheduleId);

    List<Comment> findAllCommentsByScheduleId(Long scheduleId);
}
