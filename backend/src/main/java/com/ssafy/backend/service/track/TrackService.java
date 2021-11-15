package com.ssafy.backend.service.track;

import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.info.TrackInfo;

import java.util.List;

public interface TrackService {
    int insert(TrackInfo trackInfo);
    int update(TrackInfo trackInfo);
    boolean delete(int trackId);
    List<Track> getTrackAll();
    List<Track> getTrackSubject(int subjectId);
    List<Track> getCurrentTrackSubject();
}
