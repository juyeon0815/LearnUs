package com.ssafy.backend.service.track;

import com.ssafy.backend.dao.TrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dao.TrackSubjectDao;
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
    private TrackService trackService;

    @Override
    public List<Integer> getOrdinalNo() {
        try {
            List<TrackSetting> trackSettingList = trackSettingDao.findAll();
            List<Integer> ordinalNoList = new ArrayList<>();
            for (int i = 1; i < trackSettingList.size(); i++) {
                ordinalNoList.add(trackSettingList.get(i).getOrdinalNo());
            }
            return ordinalNoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer getSemester(int ordinalNo) {
        try {
            TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(ordinalNo);

            return trackSetting.getSemester();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean insert(TrackSubjectInfo trackSubjectInfo) {
        try {
            if (trackSubjectInfo.getSubjectName().length() == 0) return false;
            TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(trackSubjectInfo.getOrdinalNo());

            TrackSubject trackSubject = TrackSubject.builder().subjectName(trackSubjectInfo.getSubjectName())
                    .trackSetting(trackSetting).nowSubject("N").build();

            trackSubjectDao.save(trackSubject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(TrackSubjectInfo trackSubjectInfo) {
        try {
            if (trackSubjectInfo.getSubjectName().length() == 0) return false;
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackSubjectInfo.getTrackSubjectId());
            TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(trackSubjectInfo.getOrdinalNo());
            trackSubject.setSubjectName(trackSubjectInfo.getSubjectName());
            trackSubject.setTrackSetting(trackSetting);

            trackSubjectDao.save(trackSubject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int trackSubjectId) {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(trackSubjectId);
            TrackSubject updateTrackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(1);
            // 관련 트랙 가져오기
            List<Track> trackList = trackDao.findTracksByTrackSubject(trackSubject);

            for (int i = 0; i < trackList.size(); i++) {
                Track track = trackList.get(i);
                trackService.delete(track.getTrackId());
            }

            trackSubjectDao.delete(trackSubject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<TrackSubject> getTrackSubjectAll() {
        try {
            List<TrackSubject> trackSubjectList = trackSubjectDao.findAll();
            trackSubjectList.remove(0); // 0번째 인덱스는 빈 값
            return trackSubjectList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TrackSubject getCurrentTrackSubject() {
        try {
            TrackSubject trackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
            return trackSubject;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean currentTrackSubjectUpdate(int newSubjectId) {
        try {
            TrackSubject nowTrackSubject = trackSubjectDao.findTrackSubjectByNowSubject("Y");
            TrackSubject newTrackSubject = trackSubjectDao.findTrackSubjectByTrackSubjectId(newSubjectId);

            nowTrackSubject.setNowSubject("N");
            newTrackSubject.setNowSubject("Y");
            trackSubjectDao.save(nowTrackSubject);
            trackSubjectDao.save(newTrackSubject);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<TrackSubject> getTrackSubjectSemester(int semester) {
        try {
            TrackSetting trackSetting = trackSettingDao.findTrackSettingBySemester(semester);

            List<TrackSubject> trackSubjectList = trackSubjectDao.findTrackSubjectsByTrackSetting(trackSetting);
            return trackSubjectList;
        } catch (Exception e) {
            return null;
        }
    }
}
