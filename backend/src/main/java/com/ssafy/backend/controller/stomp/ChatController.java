package com.ssafy.backend.controller.stomp;

import com.ssafy.backend.dto.info.ChatInfo;
import com.ssafy.backend.service.RedisService;
import com.ssafy.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ChatController {
    private static final String CHAT_EXCHANGE_NAME = "chat.exchange";
    private static final String CHAT_ADMIN_EXCHANGE_NAME = "chatAdmin.exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @MessageMapping("chat.enter.{broadcastId}")
    public void enter(@DestinationVariable int broadcastId) {
        String value = redisService.getValue("viewer"+broadcastId);
        int viewerCnt = 0;
        if (value != null || value.length() >= 0) viewerCnt = Integer.parseInt(value);
        redisService.setValue("viewer"+broadcastId, (viewerCnt+1)+"");
        rabbitTemplate.convertAndSend(CHAT_ADMIN_EXCHANGE_NAME, "broadcast." + broadcastId, value);
    }

    @MessageMapping("chat.message.{broadcastId}")
    public void send(ChatInfo chat, @DestinationVariable int broadcastId) {
        System.out.println("send : "+chat);
        userService.userChatSend(chat.getUserId(), broadcastId);

        redisService.setChatInfoValue("chat"+broadcastId, chat);

        chat.setRegDate(LocalDateTime.now());

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + broadcastId, chat);
    }
}
