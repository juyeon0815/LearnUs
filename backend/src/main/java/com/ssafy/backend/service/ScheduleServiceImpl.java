package com.ssafy.backend.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.backend.dao.ScheduleDao;
import com.ssafy.backend.dto.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public void insert(Schedule schedule) {
        scheduleDao.save(schedule);
    }

    @Override
    public void update(Schedule schedule) {
        scheduleDao.save(schedule);
    }

    @Override
    public void delete(int scheduleId) {
        Schedule schedule = scheduleDao.findScheduleByScheduleId(scheduleId);
        scheduleDao.delete(schedule);
    }

    @Override
    public List<Schedule> getScheduleAll() {
        return scheduleDao.findAll();
    }

    @Override
    public Schedule getScheduleCurrent() {
        List<Schedule> scheduleList = scheduleDao.findAll();
        LocalDate todayDate = LocalDate.now();
        for (int i=scheduleList.size()-1;i>=0;i--) {
            Schedule schedule = scheduleList.get(i);
            // LocalDate compareTo 비교 ( 0 : 일치, 양수 : 파라미터로 전달받은 날짜보다 큼, 음수 : 파라미터로 전달받은 날짜보다 작음 )
            if (todayDate.compareTo(schedule.getStartDate())>=0 && todayDate.compareTo(schedule.getExpireDate())<=0)
                return schedule;
        }
        return null;
    }
}
