package com.ssafy.backend.service;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.TrackSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    @Autowired
    private TrackSettingDao trackSettingDao;
    @Autowired
    private TrackDao trackDao;

    @Override
    public void insert(int semester, String name, String subject) {
        TrackSetting trackSetting = trackSettingDao.findTrackSettingBySemester(semester);

        Track track = new Track();
        track.setName(name);
        track.setSubject(subject);
        track.setTrackSetting(trackSetting);
        trackDao.save(track);
    }

    @Override
    public void update(Track track) {
        trackDao.save(track);
    }

    @Override
    public void delete(int trackId) {
        Track track = trackDao.findTrackByTrackId(trackId);
        trackDao.delete(track);
    }

    @Override
    public List<Track> getTrackAll() {
        List<Track> trackList = trackDao.findAll();
        trackList.remove(0);
        return trackList;
    }

    @Override
    public List<Track> getTrackCurrent1() {
        TrackSetting trackSetting = trackSettingDao.findTrackSettingBySemester(1);
        List<Track> trackList = trackDao.findTracksByTrackSetting(trackSetting);
        return trackList;
    }

    @Override
    public List<Track> getTrackCurrent2() {
        // 현재 트랙 주제 가져오기
        Track track = trackDao.findTrackByTrackId(1);
        String nowSubject = track.getSubject();

        List<Track> trackList = trackDao.findTracksBySubject(nowSubject);
        trackList.remove(0);
        return trackList;
    }
}
