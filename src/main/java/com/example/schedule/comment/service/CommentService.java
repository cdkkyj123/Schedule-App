package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.CreateCommentRequest;
import com.example.schedule.comment.dto.CreateCommentResponse;
import com.example.schedule.comment.dto.GetCommentResponse;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private static final int MAX_COMMENT_COUNT = 10;
    @Transactional
    public CreateCommentResponse saveComment(CreateCommentRequest request) {
        // 현재 몇개인지 조회
        int count = commentRepository.countByScheduleId(request.getScheduleId());
        if (count >= MAX_COMMENT_COUNT) {
            throw new IllegalArgumentException("댓글 수가 최대(" + MAX_COMMENT_COUNT + "개)입니다.");
        }
        Comment comment = new Comment(request.getContent(), request.getCommenter(), request.getPassword(), request.getScheduleId());
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> findCommentByScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllCommentsByScheduleId(scheduleId);
        return comments.stream()
                .map(GetCommentResponse::new)
                .toList();
    }
}
