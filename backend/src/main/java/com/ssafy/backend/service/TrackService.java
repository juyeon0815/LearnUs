package com.ssafy.backend.service;

import com.ssafy.backend.dto.Track;

import java.util.List;

public interface TrackService {
    public void insert(int semester, String name, String subject);
    public void update(Track track);
    public void delete(int trackId);
    public List<Track> getTrackAll();
    public List<Track> getTrackCurrent1();
    public List<Track> getTrackCurrent2();
}
