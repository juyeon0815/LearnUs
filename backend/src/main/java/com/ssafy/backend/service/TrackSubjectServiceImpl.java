package com.ssafy.backend.service;

import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dao.TrackSubjectDao;
import com.ssafy.backend.dto.TrackSetting;
import com.ssafy.backend.dto.TrackSubject;
import com.ssafy.backend.dto.TrackSubjectInfo;
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

    @Override
    public List<String> getOrdinalNo() {
        List<TrackSetting> trackSettingList = trackSettingDao.findAll();
        List<String> ordinalNoList = new ArrayList<>();
        for (int i=1;i<trackSettingList.size();i++) {
            ordinalNoList.add(trackSettingList.get(i).getOrdinalNo()+"기");
        }
        return ordinalNoList;
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

        trackSubjectDao.delete(trackSubject);
        return true;
    }

    @Override
    public List<TrackSubject> getTrackSubjectAll() {
        List<TrackSubject> trackSubjectList = trackSubjectDao.findAll();
       return trackSubjectList;
    }

    @Override
    public TrackSubject getCurrentTrackSubject() {
        TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
        if (trackSubject == null) return null;
        return trackSubject;
    }

    @Override
    public boolean currentTrackSubjectUpdate(String newSubjectName) {
        TrackSubject nowTrackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
        TrackSubject newTrackSubject = trackSubjectDao.findTrackSubjectBySubjectName(newSubjectName);
        if (nowTrackSubject == null || newTrackSubject == null) return false;

        nowTrackSubject.setNowSubject("N");
        newTrackSubject.setNowSubject("Y");
        trackSubjectDao.save(nowTrackSubject);
        trackSubjectDao.save(newTrackSubject);
        return true;
    }
}
