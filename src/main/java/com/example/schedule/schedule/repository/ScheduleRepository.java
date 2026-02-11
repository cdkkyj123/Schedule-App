package com.example.schedule.schedule.repository;

import com.example.schedule.schedule.entity.Schedule;
import com.example.schedule.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s from Schedule s where (:user is null or s.user = :user) order by s.modifiedAt desc")
    Page<Schedule> findAllByUserOrderByModifiedAtDesc(Pageable pageable, User user);
}
