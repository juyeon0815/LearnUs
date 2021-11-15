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

import java.util.Collections;
import java.util.Comparator;
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
    public int insert(TrackInfo trackInfo) {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackInfo.getSubjectId());
            // 같은 트랙 주제 내에서 트랙 이름 겹치면 return 2
            List<Track> trackList = trackDao.findTracksByTrackName(trackInfo.getTrackName());
            for (int i=0;i<trackList.size();i++) {
                Track track = trackList.get(i);
                if (track.getTrackSubject().equals(trackSubject)) return 2;
            }

            if (trackInfo.getTrackName().length()==0) return 0;
            Track track = Track.builder().trackName(trackInfo.getTrackName()).trackSubject(trackSubject).build();
            trackDao.save(track);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int update(TrackInfo trackInfo) {
        try {
            Track track = trackDao.findTrackByTrackId(trackInfo.getTrackId());
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackInfo.getSubjectId());

            // 같은 트랙 주제 내에서 트랙 이름 겹치면 return 2
            List<Track> trackList = trackDao.findTracksByTrackName(trackInfo.getTrackName());
            for (int i=0;i<trackList.size();i++) {
                Track compareTrack = trackList.get(i);
                if (compareTrack.getTrackSubject().equals(trackSubject)) return 2;
            }

            if (trackInfo.getTrackName().length()==0) return 0;
            track.setTrackName(trackInfo.getTrackName());
            track.setTrackSubject(trackSubject);
            trackDao.save(track);
            return 1;
        } catch (Exception e) {
            return 0;
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

            Collections.sort(trackList, new Comparator<Track>() {
                @Override
                public int compare(Track o1, Track o2) {
                    if (o1.getTrackSubject().getTrackSetting().getOrdinalNo() == o2.getTrackSubject().getTrackSetting().getOrdinalNo()) { // 기수가 같다면
                        // 트랙 주제 id로 정렬
                        return Integer.compare(o1.getTrackSubject().getTrackSubjectId(), o2.getTrackSubject().getTrackSubjectId());
                    }
                    // 기수 별 조회
                    return Integer.compare(o1.getTrackSubject().getTrackSetting().getOrdinalNo(), o2.getTrackSubject().getTrackSetting().getOrdinalNo());
                }
            });

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
