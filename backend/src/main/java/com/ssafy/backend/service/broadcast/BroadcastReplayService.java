package com.ssafy.backend.service.broadcast;

import com.ssafy.backend.dto.info.BroadcastReplayInfo;

import java.util.List;

public interface BroadcastReplayService {
    boolean insert(BroadcastReplayInfo broadcastReplayInfo);
    boolean update(BroadcastReplayInfo broadcastReplayInfo);
    boolean delete(int broadcastReplayId);
    List<BroadcastReplayInfo> getBroadcastReplayAll();
    List<BroadcastReplayInfo> getBroadcastReplayAllOrdinalNo(int ordinalNo);
    List<BroadcastReplayInfo> getBroadcastReplayTrack(int trackId, int ordinalNo);
    BroadcastReplayInfo getBroadcastReplay(int broadcastReplayId);
    Integer getBroadcastReplayId(int broadcastId);
    List<BroadcastReplayInfo> getBroadcastReplayLimit3(int userId);
}
