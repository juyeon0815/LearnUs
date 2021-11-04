package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastReplay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadcastReplayDao extends JpaRepository<BroadcastReplay, String> {
    BroadcastReplay findBroadcastReplayByBroadcast(Broadcast broadcast);
    BroadcastReplay findBroadcastReplayByBroadcastReplayId(int broadcastReplayId);
}
