package com.ssafy.backend.service;

import com.ssafy.backend.dao.BroadcastDao;
import com.ssafy.backend.dao.QuizDao;
import com.ssafy.backend.dao.QuizSelectDao;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.info.QuizInfo;
import com.ssafy.backend.dto.QuizSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizSelectDao quizSelectDao;
    @Autowired
    private BroadcastDao broadcastDao;

    @Override
    public boolean insert(QuizInfo quizInfo) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(quizInfo.getBroadcastId());
        if (broadcast == null) return false;

        Quiz quiz = Quiz.builder().type(quizInfo.getType())
                .question(quizInfo.getQuestion())
                .answer(quizInfo.getAnswer())
                .useYn("N").broadcast(broadcast).build();

        quizDao.save(quiz);

        if (quizInfo.getType().equals("c")) {
            for (int i=0;i<quizInfo.getQuizSelectList().size();i++) {
                QuizSelect quizSelect = QuizSelect.builder().number(i+1)
                        .view(quizInfo.getQuizSelectList().get(i)).quiz(quiz).build();

                quizSelectDao.save(quizSelect);
            }
        }
        return true;
    }

    @Override
    public boolean update(QuizInfo quizInfo) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(quizInfo.getBroadcastId());
        Quiz quiz = quizDao.findQuizByQuizId(quizInfo.getQuizId());
        if (broadcast == null || quiz == null) return false;

        quiz.setQuestion(quizInfo.getQuestion());
        quiz.setAnswer(quizInfo.getAnswer());

        // 퀴즈 타입 변경 ( 객관식 -> 주관식, ox | 주관식, ox -> 객관식 )
        if (quiz.getType().equals("c")) {
            List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
            for (int i=0;i<quizSelectList.size();i++) {
                if (!quizInfo.getType().equals("c")) quizSelectDao.delete(quizSelectList.get(i));
                else {
                    QuizSelect quizSelect = quizSelectList.get(i);
                    quizSelect.setView(quizInfo.getQuizSelectList().get(i));
                    quizSelectDao.save(quizSelect);
                }
            }
        } else if ((quiz.getType().equals("s") || quiz.getType().equals("o")) && quizInfo.getType().equals("c")) {
            for (int i=0;i<quizInfo.getQuizSelectList().size();i++) {
                QuizSelect quizSelect = QuizSelect.builder().number(i+1)
                        .view(quizInfo.getQuizSelectList().get(i)).quiz(quiz).build();

                quizSelectDao.save(quizSelect);
            }
        }
        quiz.setType(quizInfo.getType());

        quizDao.save(quiz);
        return true;
    }

    @Override
    public boolean delete(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        if (quiz == null) return false;
        quizDao.delete(quiz);
        return true;
    }

    @Override
    public List<QuizInfo> getQuizInfoAll(int broadcastId) {
        Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
        if (broadcast == null) return null;

        List<QuizInfo> quizInfoList = new ArrayList<>();
        List<Quiz> quizList = quizDao.findQuizzesByBroadcast(broadcast);

        for (int i=0;i<quizList.size();i++) {
            Quiz quiz = quizList.get(i);

            List<String> saveQuizSelectList = new ArrayList<>();
            if (quiz.getType().equals("c")) {
                List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
                for (int j=0;j<quizSelectList.size();j++) {
                    saveQuizSelectList.add(quizSelectList.get(j).getView());
                }
            }

            QuizInfo quizInfo = QuizInfo.builder().quizId(quiz.getQuizId())
                    .broadcastId(broadcastId).type(quiz.getType())
                    .question(quiz.getQuestion()).answer(quiz.getAnswer())
                    .quizSelectList(saveQuizSelectList).build();
            quizInfoList.add(quizInfo);
        }

        return quizInfoList;
    }
}
