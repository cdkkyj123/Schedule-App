package com.example.schedule.schedule.service;

import com.example.schedule.comment.dto.GetCommentResponse;
import com.example.schedule.schedule.dto.*;
import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.comment.service.CommentService;
import com.example.schedule.user.dto.SessionUser;
import com.example.schedule.user.entity.User;
import com.example.schedule.user.repository.UserRepository;
import com.example.schedule.validation.ScheduleNotFoundException;
import com.example.schedule.validation.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new UserNotFoundException("없는 회원입니다.")
        );
        Schedule schedule = new Schedule(request.getTitle(), request.getContent(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(savedSchedule);
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findOneSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정 입니다.")
        );
        List<GetCommentResponse> commentByScheduleId = commentService.findCommentByScheduleId(scheduleId);
        return new GetScheduleResponse(schedule, commentByScheduleId);
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAllSchedule() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule, commentService.findCommentByScheduleId(schedule.getId())
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findUserAllSchedule(User scheduleUser) {
        List<Schedule> schedules = scheduleRepository.findAllByUser(scheduleUser);
        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule, commentService.findCommentByScheduleId(schedule.getId())
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정 입니다.")
        );
        schedule.update(request.getTitle(), request.getContent());
        return new UpdateScheduleResponse(schedule);

    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence) {
            throw new ScheduleNotFoundException("없는 일정 입니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
