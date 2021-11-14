package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.BroadcastTrack;
import com.ssafy.backend.dto.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BroadcastTrackDao extends JpaRepository<BroadcastTrack, String> {
    List<BroadcastTrack> findBroadcastTracksByBroadcast(Broadcast broadcast);
    List<BroadcastTrack> findBroadcastTracksByTrack(Track track);
    BroadcastTrack findBroadcastTracksByBroadcastAndTrack(Broadcast broadcast, Track track);
}
