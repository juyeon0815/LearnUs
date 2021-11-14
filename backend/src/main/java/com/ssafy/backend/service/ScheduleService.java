package com.ssafy.backend.service;

import com.ssafy.backend.dto.Schedule;

import java.util.List;

public interface ScheduleService {
    boolean insert(Schedule schedule);
    boolean update(Schedule schedule);
    boolean delete(int scheduleId);
    List<Schedule> getScheduleAll();
    Schedule getScheduleCurrent();
}
