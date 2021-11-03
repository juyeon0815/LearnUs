package com.ssafy.backend.dao;

import com.ssafy.backend.dto.TrackSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackSubjectDao extends JpaRepository<TrackSubject, String> {
    TrackSubject findTrackSubjectByTrackSubjectId(int trackSubjectId);
    TrackSubject findTrackSubjectBySubjectName(String subjectName);
    TrackSubject findTrackSubjectByNowSubject(String nowSubject);
}
