package com.ssafy.backend.service;

import com.ssafy.backend.dao.AwardDao;
import com.ssafy.backend.dao.BroadcastDao;
import com.ssafy.backend.dao.GifticonDao;
import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;
import com.ssafy.backend.dto.Broadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AwardServiceImpl implements AwardService{

    @Autowired
    private AwardDao awardDao;
    @Autowired
    private BroadcastDao broadcastDao;

    @Override
    public void insert(int broadcastId, List<Attendance> chatKingList, List<Attendance> quizKingList) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            // 채팅 award 추가, 퀴즈왕 : 0 | 참가왕 : 1
            for (int i = 0; i < chatKingList.size(); i++) {
                Attendance attendance = chatKingList.get(i);
                Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 1, LocalDate.now());
                if (award != null && award.getScore() < attendance.getChatScore()) {
                    // 더 높은 점수로 바꿔주기
                    award.setScore(attendance.getChatScore());
                } else if (award == null) {
                    award = Award.builder().type(1).score(attendance.getChatScore()).date(LocalDate.now()).user(attendance.getUser()).build();
                }
                awardDao.save(award);
            }

            // 퀴즈 award 추가
            for (int i = 0; i < quizKingList.size(); i++) {
                Attendance attendance = quizKingList.get(i);
                Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 0, LocalDate.now());
                if (award != null && award.getScore() < attendance.getQuizScore()) {
                    // 더 높은 점수로 바꿔주기
                    award.setScore(attendance.getQuizScore());
                } else if (award == null) {
                    award = Award.builder().type(0).score(attendance.getQuizScore()).date(LocalDate.now()).user(attendance.getUser()).build();
                }
                awardDao.save(award);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Map<String, List<Award>> getAward() {
        try {
            Map<String, List<Award>> map = new HashMap<>();
            List<Award> chatAwardList = awardDao.findChatAwards(LocalDate.now(), 1);
            List<Award> quizAwardList = awardDao.findChatAwards(LocalDate.now(), 0);

            List<Award> saveChatAwardList = new ArrayList<>();
            List<Award> saveQuizAwardList = new ArrayList<>();

            for (int i=0;i<chatAwardList.size();i++) {
                if (saveChatAwardList.size() >= 3) break;
                Award award = chatAwardList.get(i);
                if (!saveChatAwardList.contains(award)) {
                    award.getUser().setPassword("");
                    saveChatAwardList.add(award);
                }
            }

            for (int i=0;i<quizAwardList.size();i++) {
                if (saveQuizAwardList.size() >= 3) break;
                Award award = quizAwardList.get(i);
                if (!saveQuizAwardList.contains(award)) {
                    award.getUser().setPassword("");
                    saveQuizAwardList.add(award);
                }
            }

            map.put("chat", saveChatAwardList);
            map.put("quiz", saveQuizAwardList);

            return map;
        } catch (Exception e) {
            return null;
        }
    }
}
