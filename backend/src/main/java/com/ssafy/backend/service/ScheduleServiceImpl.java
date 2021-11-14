package com.ssafy.backend.service;

import com.ssafy.backend.dao.ScheduleDao;
import com.ssafy.backend.dto.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public boolean insert(Schedule schedule) {
        try {
            scheduleDao.save(schedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Schedule schedule) {
        try {
            scheduleDao.save(schedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int scheduleId) {
        try {
            Schedule schedule = scheduleDao.findScheduleByScheduleId(scheduleId);
            scheduleDao.delete(schedule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Schedule> getScheduleAll() {
        try {
            return scheduleDao.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Schedule getScheduleCurrent() {
        try {
            List<Schedule> scheduleList = scheduleDao.findAll();
            LocalDate todayDate = LocalDate.now();
            for (int i = scheduleList.size() - 1; i >= 0; i--) {
                Schedule schedule = scheduleList.get(i);
                // LocalDate compareTo 비교 ( 0 : 일치, 양수 : 파라미터로 전달받은 날짜보다 큼, 음수 : 파라미터로 전달받은 날짜보다 작음 )
                if (todayDate.compareTo(schedule.getStartDate()) >= 0 && todayDate.compareTo(schedule.getExpireDate()) <= 0)
                    return schedule;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
