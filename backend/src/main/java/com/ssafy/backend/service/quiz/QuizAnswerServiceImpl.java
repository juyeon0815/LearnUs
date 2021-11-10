package com.ssafy.backend.service.quiz;

import com.ssafy.backend.dao.*;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.QuizAnswerInfo;
import com.ssafy.backend.dto.info.QuizRateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizAnswerServiceImpl implements QuizAnswerService{

    @Autowired
    private QuizAnswerDao quizAnswerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BroadcastDao broadcastDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private QuizDao quizDao;

    @Override
    public boolean insert(QuizAnswerInfo quizAnswerInfo) {
        Quiz quiz = quizDao.findQuizByQuizId(quizAnswerInfo.getQuizId());
        User user = userDao.findUserByUserId(quizAnswerInfo.getUserId());
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(quizAnswerInfo.getBroadcastId());
        if (quiz == null || user == null || broadcast == null) return false;

        quiz.setAttendCount(quiz.getAttendCount()+1);


        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setAnswer(quizAnswerInfo.getAnswer());
        quizAnswer.setUser(user);
        quizAnswer.setQuiz(quiz);
        quizAnswer.setSubmitTime(LocalDateTime.now());
        if (quiz.getAnswer().equals(quizAnswerInfo.getAnswer())) {
            quiz.setAnswerCount(quiz.getAnswerCount()+1);
            quizAnswer.setAnswerYn("Y");

            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);

            // 퀴즈 점수 올려주기
            attendance.setQuizScore(attendance.getQuizScore()+1);
            attendanceDao.save(attendance);
        }
        else quizAnswer.setAnswerYn("N");
        quizDao.save(quiz);
        quizAnswerDao.save(quizAnswer);
        return true;
    }

    @Override
    public List<QuizAnswer> getQuizAnswerAll(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        quiz.setUseYn("Y");
        quizDao.save(quiz);
        List<QuizAnswer> quizAnswerList = quizAnswerDao.findAnswer(quiz.getQuizId());
        // i==0 -> 1등, 5점 | i==1 -> 2등, 3점 | i==2 -> 3등, 1점
        int index = quizAnswerList.size()>=3?3:quizAnswerList.size();
        int addScore = 5;
        for (int i=0;i<index;i++) {
            QuizAnswer quizAnswer = quizAnswerList.get(i);
            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(quiz.getBroadcast(), quizAnswer.getUser());
            attendance.setQuizScore(attendance.getQuizScore()+addScore);
            addScore-=2;
        }
        return quizAnswerList;
    }

    @Override
    public Map<Object, Integer> getQuizAnswerRate(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        List<QuizAnswer> quizAnswerList = quizAnswerDao.findQuizAnswersByQuiz(quiz);
        List<Object[]> rate = new ArrayList<>();

        // 퀴즈가 객관식
        if (quiz.getAnswer() == "s") rate = quizAnswerDao.findQuizSRate(quizId);
        else rate = quizAnswerDao.findQuizRate(quizId);

        Map<Object, Integer> map = new HashMap<>();

        for (int i=0;i<rate.size();i++) {
            map.put(rate.get(i)[1], (int)((Double.parseDouble(String.valueOf(rate.get(i)[0]))/quizAnswerList.size())*100));
        }
        return map;
    }
}
