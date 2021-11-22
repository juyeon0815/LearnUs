package com.ssafy.backend.dao;

import com.ssafy.backend.dto.BroadcastReplayOrdinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BroadcastReplayOrdinalDao extends JpaRepository<BroadcastReplayOrdinal, String> {
    List<BroadcastReplayOrdinal> findBroadcastReplayOrdinalsByOrdinalNo(int ordinalNo);
    @Query(nativeQuery = true, value = "select bro.broadcast_replay_ordinal_id, bro.ordinal_no, bro.broadcast_replay_id\n" +
            "            from broadcast_replay as br join broadcast_replay_ordinal as bro join broadcast as b\n" +
            "            on br.broadcast_replay_id = bro.broadcast_replay_id and br.broadcast_id = b.broadcast_id\n" +
            "            where br.open_yn = 'Y' and bro.ordinal_no = :ordinalNo\n" +
            "            order by b.broadcast_date desc\n" +
            "            limit 3;")
    List<BroadcastReplayOrdinal> findBroadcastReplayOrdinalsByOrdinalNoLimit3(int ordinalNo);
}
