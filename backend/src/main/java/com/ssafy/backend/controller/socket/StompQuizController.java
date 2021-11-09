package com.ssafy.backend.controller.socket;

import com.ssafy.backend.dao.QuizDao;
import com.ssafy.backend.dao.QuizSelectDao;
import com.ssafy.backend.dto.*;
import com.ssafy.backend.dto.info.QuizAnswerInfo;
import com.ssafy.backend.dto.info.QuizInfo;
import com.ssafy.backend.dto.info.QuizRankInfo;
import com.ssafy.backend.service.quiz.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class StompQuizController {
    private static final String QUIZ_EXCHANGE_NAME = "quiz.exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizSelectDao quizSelectDao;
    @Autowired
    private QuizAnswerService quizAnswerService;

    @MessageMapping("quiz.start")
    public void start(int quizId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        List<QuizSelect> quizSelectList = new ArrayList<>();
        if (quiz.getType().equals("c")) quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);

        List<String> saveQuizSelectList = new ArrayList<>();

        for (int i=0;i<quizSelectList.size();i++) {
            saveQuizSelectList.add(quizSelectList.get(i).getView());
        }

        QuizInfo quizInfo = QuizInfo.builder().quizId(quizId).broadcastId(quiz.getBroadcast().getBroadcastId())
                        .type(quiz.getType()).question(quiz.getQuestion()).answer(quiz.getAnswer())
                        .quizSelectList(saveQuizSelectList).build();

        rabbitTemplate.convertAndSend(QUIZ_EXCHANGE_NAME, "quiz.0", quizInfo);
    }

    @MessageMapping("quiz.answer")
    public void answer(QuizAnswerInfo quizAnswerInfo) {
        System.out.println("퀴즈 정답제출:"+quizAnswerInfo);

        quizAnswerService.insert(quizAnswerInfo);
    }

    // 퀴즈 마감 - 순위권 보내주기 ( 정답 비율 )
    @MessageMapping("quiz.stop.{quizId}")
    public void stop(@DestinationVariable int quizId) {
        List<QuizAnswer> quizAnswerList = quizAnswerService.getQuizAnswerAll(quizId);
        Map<String, Integer> rateMap = quizAnswerService.getQuizAnswerRate(quizId);

        QuizRankInfo quizRankInfo = QuizRankInfo.builder().quizAnswerList(quizAnswerList).rateMap(rateMap).build();

        rabbitTemplate.convertAndSend(QUIZ_EXCHANGE_NAME, "quiz."+quizId, quizRankInfo);
    }
}
