package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Broadcast;
import com.ssafy.backend.dto.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizDao extends JpaRepository<Quiz, String> {
    Quiz findQuizByQuizId(int quizId);
    List<Quiz> findQuizzesByBroadcast(Broadcast broadcast);
}
