package com.ssafy.backend.service.quiz;

import com.ssafy.backend.dto.info.QuizInfo;

import java.util.List;

public interface QuizService {
    boolean insert(QuizInfo quizInfo);
    boolean update(QuizInfo quizInfo);
    boolean delete(int quizId);
    List<QuizInfo> getQuizInfoAll(int broadcastId);
}
