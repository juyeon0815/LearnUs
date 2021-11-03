package com.ssafy.backend.service;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BroadcastReplayServiceImpl implements BroadcastReplayService {

    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private BroadcastReplayDao broadcastReplayDao;

//    public void endReplayAutoUpload(int broadcastId, char autoUploadYn) throws IOException {
//        //방송 객체를 불러와 스트림키를 확인한다.
//        Broadcast broadcast = broadcastReplayDao.findBroadcastByBroadcastId(broadcastId);
//
//        //스트림키로 cdn url를 생성한다
//        String broadcastUrl = "https://d31f0osw72yf0h.cloudfront.net/" + broadcast.getStreamingKey() + "/index.m3u8";
//
//        //생성한 uri를 기반으로 다시보기 객체 생성후 삽입
//        BroadcastReplay broadcastReplay = new BroadcastReplay(broadcast.getBroadcastId(), broadcastUrl, autoUploadYn);
//
//
//        broadcastReplayDao.save(broadcastReplay);
//    }

    @Override
    public void switchOpenYn(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcast(broadcast);
        char switchOpenYn = broadcastReplay.getOpenYn() == 'Y' ? 'N' : 'Y';

        broadcastReplay.setOpenYn(switchOpenYn);

        broadcastReplayDao.save(broadcastReplay);
    }

    @Override
    public void updateReplayUrl(int broadcastId, String replayUrl) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcast(broadcast);
        broadcastReplay.setReplayUrl(replayUrl);
        broadcastReplayDao.save(broadcastReplay);
    }

    @Override
    public void deleteReplayUrl(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        BroadcastReplay broadcastReplay = broadcastReplayDao.findBroadcastReplayByBroadcast(broadcast);
        broadcastReplay.setReplayUrl("");
        broadcastReplay.setOpenYn('N');
        broadcastReplayDao.save(broadcastReplay);
    }

    @Override
    public List<BroadcastReplayInfo> getBroadcastReplayAll() {
        //반환해야하는 정보
        // 제목, 강사, 대상교육생, 방송시간
        List<BroadcastReplay> broadcastReplayList = broadcastReplayDao.findAll();
        List<BroadcastReplayInfo> broadcastReplayInfoList = new ArrayList<>();

        for(int i=0; i<broadcastReplayList.size(); i++){
            //현재 다시보기 객체 기억
            BroadcastReplay broadcastReplay = broadcastReplayList.get(i);

            // 현재 다시보기 아이디
//            int broadcastReplayId = broadcastReplay.getBroadcastReplayId();

            //방송아이디로 방송객체 조회
            Broadcast broadcast = broadcastReplay.getBroadcast();
//                    broadcastDao.findBroadcastByBroadcastId(broadcastReplayId);

            //반환용 리스트의 아이템 생성
            BroadcastReplayInfo broadcastReplayInfo = new BroadcastReplayInfo();
            broadcastReplayInfo.setBroadcast(broadcast);
//            broadcastReplayInfo.setBroadcastReplay(broadcastReplay);

            broadcastReplayInfoList.add(broadcastReplayInfo);

            System.out.println(broadcastReplayInfo);
        }
        return broadcastReplayInfoList;
    }

    @Override
    public List<BroadcastReplayInfo> getBroadcastReplayByTrack() {
        return null;
    }

    @Override
    public BroadcastReplayInfo getBroadcastReplay(int broadcastReplayId) {
        return null;
    }


}
