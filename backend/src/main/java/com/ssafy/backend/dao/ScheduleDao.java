package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDao extends JpaRepository<Schedule, String> {
    Schedule findScheduleByScheduleId(int scheduleId);
}
