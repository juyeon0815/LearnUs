package com.ssafy.backend.service;

import com.ssafy.backend.dao.AwardDao;
import com.ssafy.backend.dto.Attendance;
import com.ssafy.backend.dto.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService{

    @Autowired
    private AwardDao awardDao;

    @Override
    public void insert(List<Attendance> chatKingList, List<Attendance> quizKingList) {
        // 채팅 award 추가, 퀴즈왕 : 0 | 참가왕 : 1
        for (int i=0;i<chatKingList.size();i++) {
            Attendance attendance = chatKingList.get(i);
            Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 1, LocalDate.now());
            if (award != null && award.getScore()<attendance.getChatScore()) {
                // 더 높은 점수로 바꿔주기
                award.setScore(attendance.getChatScore());
            } else if (award == null) {
                award = Award.builder().type(1).score(attendance.getChatScore()).date(LocalDate.now())
                        .user(attendance.getUser()).build();
            }
            awardDao.save(award);
        }

        // 퀴즈 award 추가
        for (int i=0;i<quizKingList.size();i++) {
            Attendance attendance = quizKingList.get(i);
            Award award = awardDao.findAwardByUserAndTypeAndDate(attendance.getUser(), 0, LocalDate.now());
            if (award != null && award.getScore()<attendance.getQuizScore()) {
                // 더 높은 점수로 바꿔주기
                award.setScore(attendance.getQuizScore());
            } else if (award == null) {
                award = Award.builder().type(1).score(attendance.getQuizScore()).date(LocalDate.now())
                        .user(attendance.getUser()).build();
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
