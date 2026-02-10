package com.example.schedule.comment.repository;

import com.example.schedule.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JPA 쿼리 메서드
    int countByScheduleId(Long scheduleId);

    List<Comment> findAllCommentsByScheduleId(Long scheduleId);
}
