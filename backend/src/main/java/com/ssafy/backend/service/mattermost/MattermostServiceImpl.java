package com.ssafy.backend.service.mattermost;

import com.ssafy.backend.dao.MattermostDao;
import com.ssafy.backend.dao.MattermostTrackDao;
import com.ssafy.backend.dao.TrackSettingDao;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.MattermostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MattermostServiceImpl implements MattermostService{
    @Autowired
    private MattermostDao mattermostDao;
    @Autowired
    private MattermostTrackDao mattermostTrackDao;
    @Autowired
    private TrackSettingDao trackSettingDao;


    @Override
    public List<Integer> selectOrdinalNo() {
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
    public boolean insert(MattermostInfo mattermostInfo) {
        try {
            TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(mattermostInfo.getOrdinalNo());
            Mattermost mattermost = Mattermost.builder().webhook(mattermostInfo.getWebhook())
                    .name(mattermostInfo.getName()).pathName(mattermostInfo.getPathName()).trackSetting(trackSetting).build();

            mattermostDao.save(mattermost);

            for (int i = 0; i < mattermostInfo.getTrackList().size(); i++) {
                Track track = mattermostInfo.getTrackList().get(i);
                MattermostTrack mattermostTrack = MattermostTrack.builder().mattermost(mattermost).track(track).build();
                mattermostTrackDao.save(mattermostTrack);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(MattermostInfo mattermostInfo) {
        try {
            // mattermost 수정
            Mattermost mattermost = mattermostDao.findMattermostByMattermostId(mattermostInfo.getMattermostId());
            mattermost.setWebhook(mattermost.getWebhook());
            mattermost.setName(mattermostInfo.getName());
            mattermost.setPathName(mattermost.getPathName());
            TrackSetting trackSetting = trackSettingDao.findTrackSettingByOrdinalNo(mattermostInfo.getOrdinalNo());
            mattermost.setTrackSetting(trackSetting);
            mattermostDao.save(mattermost);

            // 트랙 수정 있을 때만 수정
            if (mattermostInfo.getTrackList().size() > 0) {
                List<MattermostTrack> mattermostTrackList = mattermostTrackDao.findMattermostTracksByMattermost(mattermost);

                // 기존 트랙 목록과 비교
                for (int i = 0; i < mattermostTrackList.size(); i++) {
                    // 수정된 트랙 목록과 기존의 트랙 목록 이름 비교하여 일치하지 않다면 -> 삭제
                    if (!mattermostInfo.getTrackList().contains(mattermostTrackList.get(i).getTrack())) {
                        mattermostTrackDao.delete(mattermostTrackList.get(i));
                    }
                }

                // 수정된 트랙 목록과 비교
                for (int i = 0; i < mattermostInfo.getTrackList().size(); i++) {
                    Track track = mattermostInfo.getTrackList().get(i);
                    MattermostTrack mattermostTrack = mattermostTrackDao.findMattermostTrackByMattermostAndTrack(mattermost, track);
                    // 기존 트랙에 존재하지 않다면 -> 추가
                    if (mattermostTrack == null) {
                        MattermostTrack saveMattermostTrack = MattermostTrack.builder().mattermost(mattermost).track(track).build();
                        mattermostTrackDao.save(saveMattermostTrack);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int mattermostId) {
        try {
            Mattermost mattermost = mattermostDao.findMattermostByMattermostId(mattermostId);

            mattermostDao.delete(mattermost);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MattermostInfo> getMattermostAll() {
        try {
            List<Mattermost> mattermostList = mattermostDao.findAll();

            List<Track> trackList = new ArrayList<>();
            List<MattermostInfo> mattermostInfoList = new ArrayList<>();

            for (int i = 0; i < mattermostList.size(); i++) {
                Mattermost mattermost = mattermostList.get(i);
                List<MattermostTrack> mattermostTrackList = mattermostTrackDao.findMattermostTracksByMattermost(mattermost);
                for (int j = 0; j < mattermostTrackList.size(); j++) {
                    Track track = mattermostTrackList.get(j).getTrack();
                    trackList.add(track);
                }
                MattermostInfo mattermostInfo = MattermostInfo.builder().mattermostId(mattermost.getMattermostId()).webhook(mattermost.getWebhook())
                        .name(mattermost.getName()).pathName(mattermost.getPathName()).trackList(trackList).ordinalNo(mattermost.getTrackSetting().getOrdinalNo()).build();
                mattermostInfoList.add(mattermostInfo);
                trackList = new ArrayList<>(); // 초기화
            }

            return mattermostInfoList;
        } catch (Exception e) {
            return null;
        }
    }
}
