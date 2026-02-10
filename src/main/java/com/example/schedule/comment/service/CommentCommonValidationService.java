package com.example.schedule.comment.service;

import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.user.entity.User;
import com.example.schedule.validation.CommentNotFoundException;
import com.example.schedule.validation.ForbiddenUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCommonValidationService {
    private final CommentRepository commentRepository;

    public Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException("없는 댓글입니다.")
        );
    }

    public void checkEqualUserComment(Comment comment, User user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenUserException("본인의 댓글만 조작할 수 있습니다.");
        }
    }
}
