package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastReplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BroadcastReplayDao extends JpaRepository<BroadcastReplay, String> {
    BroadcastReplay findBroadcastReplayByBroadcastReplayId(int broadcastReplayId);
    BroadcastReplay findBroadcastReplayByBroadcast(Broadcast broadcast);
    @Query(nativeQuery = true, value = "select br.broadcast_replay_id, br.broadcast_id, br.replay_url, br.open_yn\n" +
            "from broadcast as b join broadcast_replay as br\n" +
            "on b.broadcast_id = br.broadcast_id\n" +
            "order by b.broadcast_date desc\n" +
            "limit 3;")
    List<BroadcastReplay> findBroadcastReplaysByLimit3();

    @Query(nativeQuery = true, value = "select br.broadcast_replay_id, br.broadcast_id, br.replay_url, br.open_yn\n" +
            "from broadcast as b join broadcast_replay as br\n" +
            "on b.broadcast_id = br.broadcast_id\n" +
            "order by b.broadcast_date desc;")
    List<BroadcastReplay> findAllOrderByBroadcastDate();
}
