package com.ssafy.backend.service.track;

import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.info.TrackSubjectInfo;

import java.util.List;

public interface TrackSubjectService {
    List<Integer> getOrdinalNo();
    Integer getSemester(int ordinalNo);
    boolean insert(TrackSubjectInfo trackSubjectInfo);
    boolean update(TrackSubjectInfo trackSubjectInfo);
    boolean delete(int trackSubjectId);
    List<TrackSubject> getTrackSubjectAll();
    TrackSubject getCurrentTrackSubject();
    boolean currentTrackSubjectUpdate(int newSubjectId);
    List<TrackSubject> getTrackSubjectSemester(int semester);
}
