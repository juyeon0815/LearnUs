package com.ssafy.backend.service.track;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSubjectDao;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.info.TrackInfo;
import com.ssafy.backend.dto.TrackSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackSubjectDao trackSubjectDao;
    @Autowired
    private TrackDao trackDao;

    @Override
    public boolean insert(TrackInfo trackInfo) {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectBySubjectName(trackInfo.getSubjectName());
        if (trackSubject == null) return false;

        Track track = Track.builder().trackName(trackInfo.getTrackName()).trackSubject(trackSubject).build();
        trackDao.save(track);
        return true;
    }

    @Override
    public boolean update(TrackInfo trackInfo) {
        Track track = trackDao.findTrackByTrackId(trackInfo.getTrackId());
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectBySubjectName(trackInfo.getSubjectName());
        if (track == null || trackSubject == null) return false;

        track.setTrackName(trackInfo.getTrackName());
        track.setTrackSubject(trackSubject);
        trackDao.save(track);
        return true;
    }

    @Override
    public boolean delete(int trackId) {
        Track track = trackDao.findTrackByTrackId(trackId);
        if (track == null) return false;

        trackDao.delete(track);
        return true;
    }

    @Override
    public List<Track> getTrackAll() {
        List<Track> trackList = trackDao.findAll();
        trackList.remove(0); // 0번째 값은 빈 값
        return trackList;
    }

    @Override
    public List<Track> getTrackSubject(int subjectId) {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(subjectId);
        if (trackSubject == null) return null;

        List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);
        return trackList;
    }

    @Override
    public List<Track> getCurrentTrackSubject() {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
        if (trackSubject == null) return null;

        List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);
        return trackList;
    }
}
