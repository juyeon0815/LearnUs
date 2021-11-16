package com.ssafy.backend.dao;

import com.ssafy.backend.dto.BroadcastReplayOrdinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BroadcastReplayOrdinalDao extends JpaRepository<BroadcastReplayOrdinal, String> {
    List<BroadcastReplayOrdinal> findBroadcastReplayOrdinalsByOrdinalNo(int ordinalNo);
    @Query(nativeQuery = true, value = "select * from broadcast_replay_ordinal where ordinal_no = :ordinal_no order by broadcast_replay_ordinal_id desc limit 3")
    List<BroadcastReplayOrdinal> findBroadcastReplayOrdinalsByOrdinalNoLimit3(int ordinal_no);
}
