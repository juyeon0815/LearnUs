package com.ssafy.backend.dao;

import com.ssafy.backend.dto.TrackSetting;
import com.ssafy.backend.dto.TrackSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackSubjectDao extends JpaRepository<TrackSubject, String> {
    TrackSubject findTrackSubjectByTrackSubjectId(int trackSubjectId);
    TrackSubject findTrackSubjectByNowSubject(String nowSubject);
    List<TrackSubject> findTrackSubjectsByTrackSetting(TrackSetting trackSetting);
}
