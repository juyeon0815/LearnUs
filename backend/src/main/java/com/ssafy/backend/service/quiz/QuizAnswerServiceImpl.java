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

        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setUser(user);
        quizAnswer.setQuiz(quiz);
        quizAnswer.setSubmitTime(LocalDateTime.now());
        if (quiz.getAnswer().equals(quizAnswerInfo.getAnswer())) {
            quizAnswer.setAnswerYn("Y");

            Attendance attendance = attendanceDao.findAttendanceByBroadcastAndUser(broadcast, user);

            // 퀴즈 점수 올려주기
            attendance.setQuizScore(attendance.getQuizScore()+1);
            attendanceDao.save(attendance);
        }
        else quizAnswer.setAnswerYn("N");

        quizAnswerDao.save(quizAnswer);
        return true;
    }

    @Override
    public List<QuizAnswer> getQuizAnswerAll(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        List<QuizAnswer> quizAnswerList = quizAnswerDao.findTop3ByQuizAndAnswerYnByOrderBySubmitTime(quiz, "Y");
        return quizAnswerList;
    }

    @Override
    public Map<String, Integer> getQuizAnswerRate(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        List<QuizAnswer> quizAnswerList = quizAnswerDao.findQuizAnswersByQuiz(quiz);
        List<QuizRateInfo> rate = new ArrayList<>();

        // 퀴즈가 객관식
        if (quiz.getAnswer() == "s") rate = quizAnswerDao.findQuizSRate();
        else rate = quizAnswerDao.findQuizRate();

        Map<String, Integer> map = new HashMap<>();

        for (int i=0;i<rate.size();i++) {
            map.put(rate.get(i).getAnswer(), (rate.get(i).getCount()/quizAnswerList.size())*100);
        }
        return map;
    }
}
