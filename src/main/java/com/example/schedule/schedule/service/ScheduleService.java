package com.example.schedule.schedule.service;

import com.example.schedule.comment.dto.GetCommentResponse;
import com.example.schedule.schedule.dto.*;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.service.UserCommonValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;
    private final UserCommonValidationService userCommon;
    private final ScheduleCommonValidationService scheduleCommon;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        return new CreateScheduleResponse(scheduleRepository.save(schedule));
    }

    @Transactional(readOnly = true)
    public Page<Schedule> getPageByUser(int page, int size, User scheduleUser) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAllByUserOrderByModifiedAtDesc(pageable, scheduleUser);
    }

    @Transactional(readOnly = true)
    public List<GetPageResponse> findAllSchedule(Page<Schedule> schedulePage) {
        return schedulePage.stream()
                .map(schedule -> new GetPageResponse(
                        schedule, commentService.commentCount(schedule)))
                .toList();
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleCommon.checkSchedule(scheduleId);
        List<GetCommentResponse> commentByScheduleId = commentService.findCommentByScheduleId(scheduleId);
        return new GetScheduleResponse(schedule, commentByScheduleId);
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Schedule schedule = scheduleCommon.checkSchedule(scheduleId);
        scheduleCommon.checkEqualUserSchedule(schedule, user);
        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, SessionUser sessionUser) {
        User user = userCommon.checkUser(sessionUser.getId());
        Schedule schedule = scheduleCommon.checkSchedule(scheduleId);
        scheduleCommon.checkEqualUserSchedule(schedule, user);
        scheduleRepository.deleteById(scheduleId);
    }
}
