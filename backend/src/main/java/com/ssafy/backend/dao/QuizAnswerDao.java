package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.QuizAnswer;
import com.ssafy.backend.dto.info.QuizRateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizAnswerDao extends JpaRepository<QuizAnswer, String> {
    List<QuizAnswer> findQuizAnswersByQuiz(Quiz quiz);
    List<QuizAnswer> findTop3ByQuizAndAnswerYnByOrderBySubmitTime(Quiz quiz, String answerYn);
    @Query(value = "select count(QuizAnswer.answer) as count, QuizAnswer.answer as answer from QuizAnswer where QuizAnswer.quiz.quizId = :quiz_id group by QuizAnswer.answer")
    List<QuizRateInfo> findQuizRate();
    @Query(nativeQuery = true, value = "select count(QuizAnswer.answer) as count, QuizAnswer.answer as answer from QuizAnswer where QuizAnswer.quiz.quizId = :quiz_id group by QuizAnswer.answer Limit 4")
    List<QuizRateInfo> findQuizSRate();
}
