package com.ssafy.backend.service;

import com.ssafy.backend.dto.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleService {
    public void insert(Schedule schedule);
    public void update(Schedule schedule);
    public void delete(int scheduleId);
    public List<Schedule> getScheduleAll();
    public Schedule getScheduleCurrent(Date todayDate);
}
