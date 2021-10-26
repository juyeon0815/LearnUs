package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScheduleDao extends JpaRepository<Schedule, String> {
    Schedule findScheduleByScheduleId(int scheduleId);
    Schedule findScheduleByStartDate(Date startDate);
    Schedule findScheduleByExpireDate(Date expireDate);
}
