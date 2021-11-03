package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastReplay;
import com.ssafy.backend.dto.BroadcastReplayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadcastReplayDao extends JpaRepository<BroadcastReplay, String> {
    BroadcastReplay findBroadcastReplayByBroadcast(Broadcast broadcast);
//    BroadcastReplay findBroadcastReplayByBroadcastReplayId(int broadcastReplayId);
//    BroadcastReplay findBroadcastReplayByBroadcastId(int broadcastId);
//    boolean deleteBroadcastReplayByBroadcastId(int broadcastId);
}
