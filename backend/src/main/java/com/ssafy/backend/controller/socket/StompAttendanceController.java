package com.ssafy.backend.controller.socket;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 교육생들이 attendance.exchange 구독. 관리자가 출석 시작 시 여기로 메시지를 보내면 구독하고 있는 교육생에게 start 메시지 전달 -> 출석 시작
    @MessageMapping("attendance.start.{broadcastId}")
    public void attend(@DestinationVariable int broadcastId) {
        rabbitTemplate.convertAndSend(ATTENDANCE_EXCHANGE_NAME, "attendance."+broadcastId, "attendance start");
    }
}
