package com.ssafy.backend.service;

import com.ssafy.backend.dto.BroadcastReplayInfo;

import java.util.List;

public interface BroadcastReplayService {
    public boolean insert(BroadcastReplayInfo broadcastReplayInfo);
    public boolean update(BroadcastReplayInfo broadcastReplayInfo);
    public boolean delete(int broadcastReplayId);
    public List<BroadcastReplayInfo> getBroadcastReplayAll(int ordinalNo);
    public List<BroadcastReplayInfo> getBroadcastReplayTrack(int trackId, int ordinalNo);
    public BroadcastReplayInfo getBroadcastReplay(int broadcastReplayId);
}
