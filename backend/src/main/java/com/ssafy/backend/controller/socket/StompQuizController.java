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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class StompQuizController {
    private static final String QUIZ_EXCHANGE_NAME = "quiz.exchange";
    private static final String ADMIN_EXCHANGE_NAME = "admin.exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizSelectDao quizSelectDao;
    @Autowired
    private QuizAnswerService quizAnswerService;

    @MessageMapping("quiz.start.{broadcastId}")
    public void start(int quizId, @DestinationVariable int broadcastId) {
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
        Map<String, QuizInfo> map = new HashMap<>();
        map.put("quiz", quizInfo);
        rabbitTemplate.convertAndSend(QUIZ_EXCHANGE_NAME, "quiz."+broadcastId, map);
    }

    @MessageMapping("quiz.answer")
    public void answer(QuizAnswerInfo quizAnswerInfo) {
        quizAnswerService.insert(quizAnswerInfo);
    }

    // 퀴즈 마감 - 순위권 보내주기 ( 정답 비율 )
    @MessageMapping("quiz.stop.{broadcastId}")
    public void stop(int quizId, @DestinationVariable int broadcastId) {
        Quiz quiz = quizDao.findQuizByQuizId(quizId);
        List<QuizSelect> quizSelectList = quizSelectDao.findQuizSelectsByQuiz(quiz);
        List<QuizAnswer> quizAnswerList = quizAnswerService.getQuizAnswerAll(quizId);
        Map<Object, Integer> rateMap = quizAnswerService.getQuizAnswerRate(quizId);

        QuizRankInfo quizRankInfo = QuizRankInfo.builder().quizAnswerList(quizAnswerList).rateMap(rateMap).quiz(quiz).quizSelectList(quizSelectList).build();

        Map<String, QuizRankInfo> map = new HashMap<>();
        map.put("quizRank", quizRankInfo);
        rabbitTemplate.convertAndSend(ADMIN_EXCHANGE_NAME, "admin."+broadcastId, map);
        rabbitTemplate.convertAndSend(QUIZ_EXCHANGE_NAME, "quiz."+broadcastId, map);
    }
}
