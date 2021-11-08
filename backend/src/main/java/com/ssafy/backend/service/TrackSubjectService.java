package com.ssafy.backend.service;

import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.TrackSubjectInfo;

import java.util.List;

public interface TrackSubjectService {
    public List<Integer> getOrdinalNo();
    public Integer getSemester(int ordinalNo);
    public boolean insert(TrackSubjectInfo trackSubjectInfo);
    public boolean update(TrackSubjectInfo trackSubjectInfo);
    public boolean delete(int trackSubjectId);
    public List<TrackSubject> getTrackSubjectAll();
    public TrackSubject getCurrentTrackSubject();
    public boolean currentTrackSubjectUpdate(int newSubjectId);
    public List<TrackSubject> getTrackSubjectSemester(int semester);
}
