package com.ssafy.backend.service;

import com.ssafy.backend.dto.Schedule;

import java.util.List;

public interface ScheduleService {
    void insert(Schedule schedule);
    void update(Schedule schedule);
    void delete(int scheduleId);
    List<Schedule> getScheduleAll();
    Schedule getScheduleCurrent();
}
