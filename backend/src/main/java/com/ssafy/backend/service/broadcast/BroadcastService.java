package com.ssafy.backend.service.broadcast;

import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.info.BroadcastInfo;
import com.ssafy.backend.dto.info.ChatInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BroadcastService {
    void insert(BroadcastInfo broadcastInfo);
    void update(BroadcastInfo broadcastInfo);
    void delete(int broadcastId);
    void attend(int broadcastId, int userId);
    Map<String, List<Attendance>> getAttendance(int broadcastId);
    List<BroadcastInfo> getBroadcastAll(String liveCode);
    BroadcastInfo getBroadcast(int broadcastId);
    void start(int broadcastId);
    Map<String, List<Attendance>> end(int broadcastId);
    void endAttendance(int broadcastId);
    void endAttendanceDownload(int broadcastId, HttpServletResponse response) throws IOException;
    void endGifticon(int broadcastId);
    void endGifticonDownload(int broadcastId, HttpServletResponse response) throws IOException;
    void endReplayInsert(int broadcastId, String autoUploadYn);

    List<ChatInfo> getChatInfoList(int broadcastId);
}
