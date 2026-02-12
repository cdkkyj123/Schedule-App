package com.example.schedule.comment.service;

import com.example.schedule.comment.dto.*;
import com.example.schedule.comment.entity.Comment;
import com.example.schedule.comment.repository.CommentRepository;
import com.example.schedule.common.AuthConstants;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.service.ScheduleCommonValidationService;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.service.UserCommonValidationService;
import com.example.schedule.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserCommonValidationService userCommon;
    private final ScheduleCommonValidationService scheduleCommon;
    private final CommentCommonValidationService commentCommon;

    @Transactional
    public CreateCommentResponse saveComment(Long scheduleId, CreateCommentRequest request, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Schedule schedule = scheduleCommon.checkSchedule(scheduleId);
        // 현재 몇개인지 조회
        int count = commentRepository.countCommentBySchedule(schedule);
        if (count >= AuthConstants.MAX_COMMENT_COUNT) {
            throw new CommentFullException("댓글 수가 최대(" + AuthConstants.MAX_COMMENT_COUNT + "개)입니다.");
        }
        Comment comment = new Comment(request.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> getAllComment(Long scheduleId, Long userId) {
        if (userId != null && scheduleId == null) {
            userCommon.existenceUser(userId);
            // 해당 유저가 작성한 댓글들 조회
            List<Comment> commentsUsers = commentRepository.findAllByUserId(userId);
            return commentsUsers.stream().map(GetCommentResponse::new).toList();
        } else if (userId == null && scheduleId != null) {
            scheduleCommon.existenceSchedule(scheduleId);
            // 해당 게시글에 작성된 댓글들 조회
            List<Comment> commentsSchedules = commentRepository.findAllByScheduleId(scheduleId);
            return commentsSchedules.stream().map(GetCommentResponse::new).toList();
        } else if (userId != null) {
            userCommon.existenceUser(userId);
            scheduleCommon.existenceSchedule(scheduleId);
            // 해당 유저가 해당 게시글에 작성한 댓글들 조회
            return commentRepository.findCommentsByUserIdAndScheduleId(userId, scheduleId)
                    .stream().map(GetCommentResponse::new).toList();
        }
        // 전체 댓글 조회
        List<Comment> commentsAll = commentRepository.findAll();
        return commentsAll.stream().map(GetCommentResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public GetCommentResponse getOneComment(Long commentId) {
        Comment comment = commentCommon.checkComment(commentId);
        return new GetCommentResponse(comment);
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long commentId, UpdateCommentRequest request, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Comment comment = commentCommon.checkComment(commentId);
        commentCommon.checkEqualUserComment(comment, user);
        comment.update(request.getContent());
        return new UpdateCommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Comment comment = commentCommon.checkComment(commentId);
        commentCommon.checkEqualUserComment(comment, user);
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> findCommentByScheduleId(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllCommentsByScheduleId(scheduleId);
        return comments.stream()
                .map(GetCommentResponse::new)
                .toList();
    }

    public int commentCount(Schedule schedule) {
        return commentRepository.countCommentBySchedule(schedule);
    }
}
