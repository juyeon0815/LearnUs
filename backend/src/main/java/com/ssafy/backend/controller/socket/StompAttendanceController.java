package com.ssafy.backend.controller.socket;

import com.ssafy.backend.service.RedisService;
import com.ssafy.backend.service.broadcast.BroadcastService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class StompAttendanceController {
    private static final String ATTENDANCE_EXCHANGE_NAME = "attendance.exchange";
    private static final int expireMin = 10;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BroadcastService broadcastService;

    // 교육생들이 attendance.exchange 구독. 관리자가 출석 시작 시 여기로 메시지를 보내면 구독하고 있는 교육생에게 start 메시지 전달 -> 출석 시작
    @MessageMapping("attendance.start.{broadcastId}")
    public void attend(@DestinationVariable int broadcastId) {
        redisService.setValueExpire("attendance"+broadcastId, "attendance", (long) expireMin);
        rabbitTemplate.convertAndSend(ATTENDANCE_EXCHANGE_NAME, "attendance."+broadcastId, "attendance start");
    }

    // 출석 종료
    @MessageMapping("attendance.stop.{broadcastId}")
    public void attendStop(@DestinationVariable int broadcastId) {
        redisService.delete("attendance"+broadcastId);
        rabbitTemplate.convertAndSend(ATTENDANCE_EXCHANGE_NAME, "attendance."+broadcastId, "attendance stop");
    }

    @MessageMapping("attendance.attend")
    public void attend(int userId, int broadcastId) {
        broadcastService.attend(broadcastId, userId);
    }

    @MessageMapping("broadcast.start.{broadcastId}")
    public void broadcastStart(@DestinationVariable int broadcastId) {
        rabbitTemplate.convertAndSend(ATTENDANCE_EXCHANGE_NAME, "attendance."+broadcastId, "broadcast start");
    }

    @MessageMapping("broadcast.stop.{broadcastId}")
    public void broadcastStop(@DestinationVariable int broadcastId) {
        rabbitTemplate.convertAndSend(ATTENDANCE_EXCHANGE_NAME, "attendance."+broadcastId, "broadcast stop");
    }
}
