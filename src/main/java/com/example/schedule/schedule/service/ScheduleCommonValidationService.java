package com.example.schedule.schedule.service;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.schedule.repository.ScheduleRepository;
import com.example.schedule.user.entity.User;
import com.example.schedule.validation.ForbiddenUserException;
import com.example.schedule.validation.ScheduleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleCommonValidationService {
    private final ScheduleRepository scheduleRepository;

    public Schedule checkSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ScheduleNotFoundException("없는 일정입니다.")
        );
    }

    public void existenceSchedule(Long scheduleId) {
        boolean exSchedule = scheduleRepository.existsById(scheduleId);
        if (!exSchedule) {
            throw new ScheduleNotFoundException("없는 일정입니다.");
        }
    }

    public void checkEqualUserSchedule(Schedule schedule, User user) {
        if (!schedule.getUser().getId().equals(user.getId())) {
            throw new ForbiddenUserException("본인의 일정만 조작할 수 있습니다.");
        }
    }
}
