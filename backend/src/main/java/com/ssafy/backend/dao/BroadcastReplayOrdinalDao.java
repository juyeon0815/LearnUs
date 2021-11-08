package com.ssafy.backend.dao;

import com.ssafy.backend.dto.BroadcastReplayOrdinal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BroadcastReplayOrdinalDao extends JpaRepository<BroadcastReplayOrdinal, String> {
    List<BroadcastReplayOrdinal> findBroadcastReplayOrdinalsByOrdinalNo(int ordinalNo);
}
