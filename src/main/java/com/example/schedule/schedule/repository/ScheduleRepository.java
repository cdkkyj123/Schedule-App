package com.example.schedule.schedule.repository;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUser(User user);
}
