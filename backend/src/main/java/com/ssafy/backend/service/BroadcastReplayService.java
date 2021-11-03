package com.ssafy.backend.service;

import com.ssafy.backend.dto.BroadcastReplayInfo;

import java.util.List;

public interface BroadcastReplayService {
    public boolean insert(BroadcastReplayInfo broadcastReplayInfo);
    public boolean update(BroadcastReplayInfo broadcastReplayInfo);
    public boolean delete(int broadcastReplayId);
    public List<BroadcastReplayInfo> getBroadcastReplayAll();
    public List<BroadcastReplayInfo> getBroadcastReplayTrack(String trackName);
    public BroadcastReplayInfo getBroadcastReplay(int broadcastReplayId);
}
