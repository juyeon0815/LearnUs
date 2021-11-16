package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastReplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BroadcastReplayDao extends JpaRepository<BroadcastReplay, String> {
    BroadcastReplay findBroadcastReplayByBroadcastReplayId(int broadcastReplayId);
    BroadcastReplay findBroadcastReplayByBroadcast(Broadcast broadcast);
    @Query(nativeQuery = true, value = "select * from broadcast_replay order by broadcast_replay_id desc limit 3")
    List<BroadcastReplay> findBroadcastReplaysByLimit3();
}
