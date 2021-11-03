package com.ssafy.backend.service;

import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.TrackSubjectInfo;

import java.util.List;

public interface TrackSubjectService {
    public List<Integer> getOrdinalNo();
    public boolean insert(TrackSubjectInfo trackSubjectInfo);
    public boolean update(TrackSubjectInfo trackSubjectInfo);
    public boolean delete(int trackSubjectId);
    public List<TrackSubject> getTrackSubjectAll();
    public TrackSubject getCurrentTrackSubject();
    public boolean currentTrackSubjectUpdate(String newSubjectName);
}
