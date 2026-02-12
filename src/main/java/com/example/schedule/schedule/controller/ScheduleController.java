package com.example.schedule.schedule.controller;

import com.example.schedule.common.service.CommonService;
import com.example.schedule.schedule.dto.*;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.service.ScheduleService;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CommonService commonService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> postSchedule(
            @RequestBody CreateScheduleRequest request,
            HttpSession session
    ) {
        // session에서 로그인 정보를 가져옴
        SessionUser sessionUser = commonService.getSessionUser(session);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request, sessionUser));
    }

    @GetMapping
    public ResponseEntity<List<GetPageResponse>> findAll(
            @RequestParam(defaultValue = "0") int page, // 현재 페이지
            @RequestParam(defaultValue = "10") int size, // 크기
            @RequestParam(required = false) User scheduleUser
    ) {
        Page<Schedule> schedulePage = scheduleService.getPageByUser(page, size, scheduleUser);
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAllSchedule(schedulePage));
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> findOne(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOneSchedule(scheduleId));
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        UpdateScheduleResponse result = scheduleService.updateSchedule(scheduleId, request, sessionUser);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId,
            HttpSession session
    ) {
        SessionUser sessionUser = commonService.getSessionUser(session);
        scheduleService.deleteSchedule(scheduleId, sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

