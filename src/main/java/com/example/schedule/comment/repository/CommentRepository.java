package com.example.schedule.comment.repository;

import com.example.schedule.comment.dto.GetCommentResponse;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countCommentBySchedule(Schedule schedule);

    List<Comment> findAllCommentsByScheduleId(Long scheduleId);

    List<Comment> findCommentsByUserAndSchedule(User user, Schedule schedule);

    List<Comment> findAllByUserId(Long id);

    List<Comment> findAllByScheduleId(Long id);
}
