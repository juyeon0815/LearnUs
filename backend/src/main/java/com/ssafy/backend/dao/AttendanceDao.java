package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttendanceDao extends JpaRepository<Attendance, String> {
    Attendance findAttendanceByBroadcastAndUser(Broadcast broadcast, User user);
    List<Attendance> findAttendancesByBroadcastAndAttend(Broadcast broadcast, String attend);

    @Query(nativeQuery = true, value = "select * from attendance where broadcast_id = :broadcastId order by user_id")
    List<Attendance> findAttendancesByBroadcastOrderByUserId(int broadcastId);

    @Query(nativeQuery = true, value = "select * from attendance where quiz_score > 0 and broadcast_id = :broadcastId order by quiz_score desc Limit 3")
    List<Attendance> findQuizKing(int broadcastId);
    @Query(nativeQuery = true, value = "select * from attendance where chat_score > 0 and broadcast_id = :broadcastId order by chat_score desc Limit 3")
    List<Attendance> findChatKing(int broadcastId);

    @Query(nativeQuery = true, value = "select count(*) from attendance where broadcast_id = :broadcastId")
    Integer findTotalAttendCount(int broadcastId);
    @Query(nativeQuery = true, value = "select count(*) from attendance where broadcast_id = :broadcastId and attend = 'Y' ")
    Integer findCompleteAttendCount(int broadcastId);

}
