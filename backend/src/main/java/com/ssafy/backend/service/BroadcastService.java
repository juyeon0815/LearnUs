package com.ssafy.backend.service;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.BroadcastInfo;

import java.util.List;
import java.util.Map;

public interface BroadcastService {
    public void insert(BroadcastInfo broadcastInfo);
    public void update(BroadcastInfo broadcastInfo);
    public void delete(int broadcastId);
    public void attend(int broadcastId, int userId);
    public Map<String, List<Attendance>> getAttendance(int broadcastId);
    public List<BroadcastInfo> getBroadcastAll();
    public void start(int broadcastId);
    public void endAttendance(int broadcastId);
}
