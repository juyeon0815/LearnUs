package com.ssafy.backend.dao;

import com.ssafy.backend.dto.Quiz;
import com.ssafy.backend.dto.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizAnswerDao extends JpaRepository<QuizAnswer, String> {
    List<QuizAnswer> findQuizAnswersByQuiz(Quiz quiz);
    @Query(nativeQuery = true, value = "select * from quiz_answer where answer_yn='Y' and quiz_id = :quizId limit 3")
    List<QuizAnswer> findAnswer(int quizId);
    @Query(nativeQuery = true, value = "select count(answer) as count, answer from quiz_answer where quiz_id = :quizId group by answer")
    List<Object[]> findQuizRate(int quizId);
    @Query(nativeQuery = true, value = "select count(answer) as count, answer from quiz_answer where quiz_id = :quizId group by answer Limit 4")
    List<Object[]> findQuizSRate(int quizId);
}
