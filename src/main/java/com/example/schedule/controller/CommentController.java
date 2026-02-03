package com.example.schedule.controller;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.dto.GetCommentResponse;
import com.example.schedule.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.status(403).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
