package com.example.schedule.comment.controller;

import com.example.schedule.comment.dto.CreateCommentRequest;
import com.example.schedule.comment.dto.CreateCommentResponse;
import com.example.schedule.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody CreateCommentRequest request) {
        CreateCommentResponse result = null;
        try {
            result = commentService.saveComment(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
