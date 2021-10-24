package com.ssafy.backend.service;

import com.ssafy.backend.dao.ScheduleDao;
import com.ssafy.backend.dto.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleDao scheduleDao;

    public Date convertDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

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
        List<Schedule> scheduleList = scheduleDao.findAll();
        for (Schedule schedule : scheduleList) {
            schedule.setScheduleDate(convertDate(schedule.getScheduleDate()));
        }
        return scheduleList;
    }

    @Override
    public Schedule getScheduleCurrent(Date todayDate) {
        Schedule schedule = scheduleDao.findScheduleByScheduleDate(todayDate);
        schedule.setScheduleDate(convertDate(schedule.getScheduleDate()));
        return schedule;
    }
}
