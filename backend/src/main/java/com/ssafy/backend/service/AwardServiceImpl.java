package com.ssafy.backend.service;

import com.ssafy.backend.dao.AwardDao;
import com.ssafy.backend.dao.BroadcastDao;
import com.ssafy.backend.dao.GifticonDao;
import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Gifticon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService{

    @Autowired
    private AwardDao awardDao;
    @Autowired
    private GifticonDao gifticonDao;
    @Autowired
    private BroadcastDao broadcastDao;

    @Override
    public void insert(int broadcastId, List<Attendance> chatKingList, List<Attendance> quizKingList) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        // 채팅 award 추가, 퀴즈왕 : 0 | 참가왕 : 1
        for (int i=0;i<chatKingList.size();i++) {
            Attendance attendance = chatKingList.get(i);
            Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 1, LocalDate.now());
            Gifticon gifticon = gifticonDao.findGifticonByUserAndBroadcast(attendance.getUser(), broadcast);
            if (award != null && award.getScore()<attendance.getChatScore()) {
                // 더 높은 점수로 바꿔주기
                award.setScore(attendance.getChatScore());
            } else if (award == null) {
                String gifticonYn = gifticon == null?"N":"Y";
                award = Award.builder().type(1).score(attendance.getChatScore()).date(LocalDate.now())
                        .user(attendance.getUser()).gifticonYn(gifticonYn).build();
            }
            awardDao.save(award);
        }

        // 퀴즈 award 추가
        for (int i=0;i<quizKingList.size();i++) {
            Attendance attendance = quizKingList.get(i);
            Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 0, LocalDate.now());
            Gifticon gifticon = gifticonDao.findGifticonByUserAndBroadcast(attendance.getUser(), broadcast);
            if (award != null && award.getScore()<attendance.getQuizScore()) {
                // 더 높은 점수로 바꿔주기
                award.setScore(attendance.getQuizScore());
            } else if (award == null) {
                String gifticonYn = gifticon == null?"N":"Y";
                award = Award.builder().type(1).score(attendance.getQuizScore()).date(LocalDate.now())
                        .user(attendance.getUser()).gifticonYn(gifticonYn).build();
            }
            awardDao.save(award);
        }
    }

    @Override
    public List<Award> getAward() {
        List<Award> awardList = awardDao.findAwards(LocalDate.now());
        return awardList;
    }
}
