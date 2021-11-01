package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.BroadcastInfo;

import java.util.List;

public interface BroadcastService {
    public void insert(BroadcastInfo broadcastInfo);
    public void update(BroadcastInfo broadcastInfo);
    public void delete(int broadcastId);
    public void attend(int broadcastId, int userId);
    public List<Attendance> getAttendance(int broadcastId);
    public List<BroadcastInfo> getBroadcastAll();
}
