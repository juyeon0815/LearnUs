package com.ssafy.backend.service;

import com.ssafy.backend.dto.QuizInfo;

import java.util.List;

public interface QuizService {
    public boolean insert(QuizInfo quizInfo);
    public boolean update(QuizInfo quizInfo);
    public boolean delete(int quizId);
    public List<QuizInfo> getQuizInfoAll(int broadcastId);
}
