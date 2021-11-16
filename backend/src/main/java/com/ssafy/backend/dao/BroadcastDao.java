package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BroadcastDao extends JpaRepository<Broadcast, String> {
    Broadcast findBroadcastByBroadcastId(int broadcastId);
    List<Broadcast> findBroadcastsByLiveCodeOrderByBroadcastDateDesc(String liveCode);
}
