package com.ssafy.backend.service.track;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dao.TrackSubjectDao;
import com.ssafy.backend.dao.UserDao;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.TrackSubjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackSubjectServiceImpl implements TrackSubjectService{

    @Autowired
    private TrackSettingDao trackSettingDao;
    @Autowired
    private TrackSubjectDao trackSubjectDao;
    @Autowired
    private TrackDao trackDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Integer> getOrdinalNo() {
        List<TrackSetting> trackSettingList = trackSettingDao.findAll();
        List<Integer> ordinalNoList = new ArrayList<>();
        for (int i=1;i<trackSettingList.size();i++) {
            ordinalNoList.add(trackSettingList.get(i).getOrdinalNo());
        }
        return ordinalNoList;
    }

    @Override
    public Integer getSemester(int ordinalNo) {
        TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(ordinalNo);
        if (trackSetting == null) return 0;

        return trackSetting.getSemester();
    }

    @Override
    public boolean insert(TrackSubjectInfo trackSubjectInfo) {
        TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(trackSubjectInfo.getOrdinalNo());
        if (trackSetting == null) return false;

        TrackSubject trackSubject = TrackSubject.builder().subjectName(trackSubjectInfo.getSubjectName())
                .trackSetting(trackSetting).nowSubject("N").build();

        trackSubjectDao.save(trackSubject);
        return true;
    }

    @Override
    public boolean update(TrackSubjectInfo trackSubjectInfo) {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackSubjectInfo.getTrackSubjectId());
        TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(trackSubjectInfo.getOrdinalNo());
        if (trackSubject == null || trackSetting == null) return false;
        trackSubject.setSubjectName(trackSubjectInfo.getSubjectName());
        trackSubject.setTrackSetting(trackSetting);

        trackSubjectDao.save(trackSubject);
        return true;
    }

    @Override
    public boolean delete(int trackSubjectId) {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackSubjectId);
        if (trackSubject == null) return false;

        // 관련 트랙 가져오기
        List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);

        for (int i=0;i<trackList.size();i++) {
            Track track = trackList.get(i);
            // 트랙과 관련된 유저 가져오기
            List<User> userList = userDao.findUserByTrack(track);
            for (int j=0;j<userList.size();j++) {
                User user = userList.get(j);
                Track saveTrack = trackDao.findTrackByTrackId(1);
                // 유저 연관 끊어주기
                user.setTrack(saveTrack);
                userDao.save(user);
            }
        }

        trackSubjectDao.delete(trackSubject);
        return true;
    }

    @Override
    public List<TrackSubject> getTrackSubjectAll() {
        List<TrackSubject> trackSubjectList = trackSubjectDao.findAll();
        trackSubjectList.remove(0); // 0번째 인덱스는 빈 값
       return trackSubjectList;
    }

    @Override
    public TrackSubject getCurrentTrackSubject() {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
        if (trackSubject == null) return null;
        return trackSubject;
    }

    @Override
    public boolean currentTrackSubjectUpdate(int newSubjectId) {
        TrackSubject nowTrackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
        TrackSubject newTrackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(newSubjectId);
        if (nowTrackSubject == null || newTrackSubject == null) return false;

        nowTrackSubject.setNowSubject("N");
        newTrackSubject.setNowSubject("Y");
        trackSubjectDao.save(nowTrackSubject);
        trackSubjectDao.save(newTrackSubject);
        return true;
    }

    @Override
    public List<TrackSubject> getTrackSubjectSemester(int semester) {
        TrackSetting trackSetting = trackSettingDao.findTrackSettingBySemester(semester);
        if (trackSetting == null) return null;

        List<TrackSubject> trackSubjectList = trackSubjectDao.findTrackSubjectsByTrackSetting(trackSetting);
        return trackSubjectList;
    }
}
