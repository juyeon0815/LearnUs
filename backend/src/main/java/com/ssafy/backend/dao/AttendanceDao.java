package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastTrack;
import com.ssafy.backend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceDao extends JpaRepository<Attendance, String> {
    Attendance findAttendanceByBroadcastAndUser(Broadcast broadcast, User user);
    List<Attendance> findAttendancesByBroadcast(Broadcast broadcast);
    List<Attendance> findAttendancesByBroadcastAndBroadcastTrack(Broadcast broadcast, BroadcastTrack broadcastTrack);
}
