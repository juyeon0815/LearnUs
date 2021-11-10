package com.ssafy.backend.service.quiz;

import com.ssafy.backend.dto.QuizAnswer;
import com.ssafy.backend.dto.info.QuizAnswerInfo;

import java.util.List;
import java.util.Map;

public interface QuizAnswerService {
    boolean insert(QuizAnswerInfo quizAnswerInfo);
    List<QuizAnswer> getQuizAnswerAll(int quizId);
    Map<Object, Integer> getQuizAnswerRate(int quizId);
}
