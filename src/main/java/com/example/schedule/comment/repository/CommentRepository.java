package com.example.schedule.comment.repository;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countCommentBySchedule(Schedule schedule);

    List<Comment> findAllCommentsByScheduleId(Long scheduleId);

    List<Comment> findCommentsByUserIdAndScheduleId(Long userId, Long scheduleId);

    List<Comment> findAllByUserId(Long id);

    List<Comment> findAllByScheduleId(Long id);
}
