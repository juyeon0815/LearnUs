package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.QuizSelect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizSelectDao extends JpaRepository<QuizSelect, String> {
    List<QuizSelect> findQuizSelectsByQuiz(Quiz quiz);
}
