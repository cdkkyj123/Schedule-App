package com.example.schedule.comment.controller;

import com.example.schedule.comment.dto.*;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.common.AuthConstants;
import com.example.schedule.common.service.CommonService;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> save(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(scheduleId, request, sessionUser));
    }

    @GetMapping
    public ResponseEntity<List<GetCommentResponse>> getAll(
            @RequestParam(required = false) Schedule commentSchedule,
            @RequestParam(required = false) User commentUser
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComment(commentSchedule, commentUser));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<GetCommentResponse> getOne(
            @PathVariable Long commentId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getOneComment(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> update(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, request, sessionUser));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        commentService.deleteComment(commentId, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
