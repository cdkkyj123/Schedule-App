package com.example.schedule.service;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.dto.GetCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
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
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCommenter(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> findCommentByScheduleId(Long scheduleId) {
        boolean existence = commentRepository.existsById(scheduleId);
        if (!existence) {
            throw new IllegalStateException("해당 일정이 존재하지 않습니다.");
        }
        List<Comment> comments = commentRepository.findAllCommentsByScheduleId(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCommenter(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
