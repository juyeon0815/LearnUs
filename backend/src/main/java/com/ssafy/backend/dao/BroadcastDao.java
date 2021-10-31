package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadcastDao extends JpaRepository<Broadcast, String> {
    Broadcast findBroadcastByBroadcastId(int broadcastId);
}
