package com.ssafy.backend.service.quiz;

import com.ssafy.backend.dao.BroadcastDao;
import com.ssafy.backend.dao.QuizAnswerDao;
import com.ssafy.backend.dao.QuizDao;
import com.ssafy.backend.dao.QuizSelectDao;
import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.QuizAnswer;
import com.ssafy.backend.dto.info.QuizInfo;
import com.ssafy.backend.dto.QuizSelect;
import com.ssafy.backend.dto.info.QuizRankInfo;
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
    @Autowired
    private QuizAnswerDao quizAnswerDao;

    @Override
    public boolean insert(QuizInfo quizInfo) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(quizInfo.getBroadcastId());

            Quiz quiz = Quiz.builder().type(quizInfo.getType())
                    .question(quizInfo.getQuestion())
                    .answer(quizInfo.getAnswer())
                    .useYn("N").broadcast(broadcast).build();

            quizDao.save(quiz);

            if (quizInfo.getType().equals("c")) {
                for (int i = 0; i < quizInfo.getQuizSelectList().size(); i++) {
                    QuizSelect quizSelect = QuizSelect.builder().number(i + 1)
                            .view(quizInfo.getQuizSelectList().get(i)).quiz(quiz).build();

                    quizSelectDao.save(quizSelect);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(QuizInfo quizInfo) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(quizInfo.getBroadcastId());
            Quiz quiz = quizDao.findQuizByQuizId(quizInfo.getQuizId());

            quiz.setQuestion(quizInfo.getQuestion());
            quiz.setAnswer(quizInfo.getAnswer());

            // 퀴즈 타입 변경 ( 객관식 -> 주관식, ox | 주관식, ox -> 객관식 )
            if (quiz.getType().equals("c")) {
                List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
                for (int i = 0; i < quizSelectList.size(); i++) {
                    if (!quizInfo.getType().equals("c")) quizSelectDao.delete(quizSelectList.get(i));
                    else {
                        QuizSelect quizSelect = quizSelectList.get(i);
                        quizSelect.setView(quizInfo.getQuizSelectList().get(i));
                        quizSelectDao.save(quizSelect);
                    }
                }
            } else if ((quiz.getType().equals("s") || quiz.getType().equals("o")) && quizInfo.getType().equals("c")) {
                for (int i = 0; i < quizInfo.getQuizSelectList().size(); i++) {
                    QuizSelect quizSelect = QuizSelect.builder().number(i + 1)
                            .view(quizInfo.getQuizSelectList().get(i)).quiz(quiz).build();

                    quizSelectDao.save(quizSelect);
                }
            }
            quiz.setType(quizInfo.getType());

            quizDao.save(quiz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(int quizId) {
        try {
            Quiz quiz = quizDao.findQuizByQuizId(quizId);
            quizDao.delete(quiz);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<QuizInfo> getQuizInfoAll(int broadcastId) {
        try {
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);

            List<QuizInfo> quizInfoList = new ArrayList<>();
            List<Quiz> quizList = quizDao.findQuizzesByBroadcast(broadcast);

            for (int i = 0; i < quizList.size(); i++) {
                Quiz quiz = quizList.get(i);

                List<String> saveQuizSelectList = new ArrayList<>();
                if (quiz.getType().equals("c")) {
                    List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
                    for (int j = 0; j < quizSelectList.size(); j++) {
                        saveQuizSelectList.add(quizSelectList.get(j).getView());
                    }
                }

                QuizInfo quizInfo = QuizInfo.builder().quizId(quiz.getQuizId())
                        .broadcastId(broadcastId).type(quiz.getType())
                        .question(quiz.getQuestion()).answer(quiz.getAnswer())
                        .quizSelectList(saveQuizSelectList).useYn(quiz.getUseYn()).build();
                quizInfoList.add(quizInfo);
            }

            return quizInfoList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<QuizRankInfo> getQuizInfoAllAfterBroadcast(int broadcastId) {
        try {
            List<QuizRankInfo> quizRankInfoList = new ArrayList<>();
            Broadcast broadcast = broadcastDao.findBroadcastByBroadcastId(broadcastId);
            List<Quiz> quizList = quizDao.findQuizzesByBroadcast(broadcast);

            for (int i = 0; i < quizList.size(); i++) {
                Quiz quiz = quizList.get(i);

                List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
                List<QuizAnswer> quizAnswerList = quizAnswerDao.findAnswer(quiz.getQuizId());

                for (int j=0;j<quizAnswerList.size();j++) {
                    QuizAnswer quizAnswer = quizAnswerList.get(j);
                    quizAnswer.getUser().setPassword("");
                }

                QuizRankInfo quizRankInfo = QuizRankInfo.builder().quizAnswerList(quizAnswerList)
                        .quiz(quiz).quizSelectList(quizSelectList).build();

                quizRankInfoList.add(quizRankInfo);
            }
            return quizRankInfoList;
        } catch (Exception e) {
            return null;
        }
    }
}
