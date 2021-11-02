package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.TrackSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackDao extends JpaRepository<Track, String> {
    Track findTRACKByName(String Name);
    Track findTrackByTrackId(int trackId);
    List<Track> findTracksByTrackSetting(TrackSetting trackSetting);
    List<Track> findTracksBySubject(String subject);
}
