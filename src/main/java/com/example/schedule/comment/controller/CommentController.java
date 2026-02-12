package com.example.schedule.comment.controller;

import com.example.schedule.comment.dto.*;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.common.dto.ApiResponse;
import com.example.schedule.common.service.CommonService;
import com.example.schedule.user.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<CreateCommentResponse>> save(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.ok(ApiResponse.success(commentService.saveComment(scheduleId, request, sessionUser)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetCommentResponse>>> getAll(
            @RequestParam(required = false) Long scheduleId,
            @RequestParam(required = false) Long userId
    ) {
        return ResponseEntity.ok(ApiResponse.success(commentService.getAllComment(scheduleId, userId)));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ApiResponse<GetCommentResponse>> getOne(
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(ApiResponse.success(commentService.getOneComment(commentId)));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> update(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.ok(ApiResponse.success(commentService.updateComment(commentId, request, sessionUser)));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        commentService.deleteComment(commentId, sessionUser);
        return ResponseEntity.ok(ApiResponse.successWithNoContent());
    }
}
