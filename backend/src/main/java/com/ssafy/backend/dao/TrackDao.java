package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.TrackSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackDao extends JpaRepository<Track, String> {
    Track findTrackByTrackName(String trackName);
    Track findTrackByTrackId(int trackId);
    List<Track> findTracksByTrackSubject(TrackSubject trackSubject);
    List<Track> findTracksByTrackName(String trackName);
}
