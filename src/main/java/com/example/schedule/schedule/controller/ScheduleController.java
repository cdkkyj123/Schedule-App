package com.example.schedule.schedule.controller;

import com.example.schedule.common.AuthConstants;
import com.example.schedule.schedule.dto.*;
import com.example.schedule.schedule.service.ScheduleService;
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
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> postSchedule(@RequestBody CreateScheduleRequest request, HttpSession session) {
        // session에서 로그인 정보를 가져옴
        SessionUser sessionUser = (SessionUser) session.getAttribute(AuthConstants.LOGIN_USER);
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CreateScheduleResponse result = scheduleService.save(request, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> findOne(@PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOneSchedule(scheduleId));
    }

    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> findAll(@RequestParam(required = false) User scheduleUser) {

        if(scheduleUser == null) {
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedule());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findUserAllSchedule(scheduleUser));
        }
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ) {
        UpdateScheduleResponse result = scheduleService.updateSchedule(scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

