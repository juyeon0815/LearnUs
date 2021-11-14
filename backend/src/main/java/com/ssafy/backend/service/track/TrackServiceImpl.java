package com.ssafy.backend.service.track;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSubjectDao;
import com.ssafy.backend.dao.UserDao;
import com.ssafy.backend.dto.Track;
import com.ssafy.backend.dto.User;
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
    @Autowired
    private UserDao userDao;

    @Override
    public boolean insert(TrackInfo trackInfo) {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectBySubjectName(trackInfo.getSubjectName());

            Track track = Track.builder().trackName(trackInfo.getTrackName()).trackSubject(trackSubject).build();
            trackDao.save(track);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(TrackInfo trackInfo) {
        try {
            Track track = trackDao.findTrackByTrackId(trackInfo.getTrackId());
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectBySubjectName(trackInfo.getSubjectName());

            track.setTrackName(trackInfo.getTrackName());
            track.setTrackSubject(trackSubject);
            trackDao.save(track);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int trackId) {
        try {
            Track track = trackDao.findTrackByTrackId(trackId);
            // 트랙 삭제 시 관련된 User 객체 track 변경
            Track updateTrack = trackDao.findTrackByTrackId(1);
            List<User> userList = userDao.findUserByTrack(track);
            for (int i=0;i<userList.size();i++) {
                User user = userList.get(i);
                user.setTrack(updateTrack);
                userDao.save(user);
            }
            trackDao.delete(track);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Track> getTrackAll() {
        try {
            List<Track> trackList = trackDao.findAll();
            trackList.remove(0); // 0번째 값은 빈 값
            return trackList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Track> getTrackSubject(int subjectId) {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(subjectId);

            List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);
            return trackList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Track> getCurrentTrackSubject() {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");

            List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);
            return trackList;
        } catch (Exception e) {
            return null;
        }
    }
}
