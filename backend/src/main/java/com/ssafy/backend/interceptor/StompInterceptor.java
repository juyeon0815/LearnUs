package com.ssafy.backend.interceptor;

import com.ssafy.backend.controller.socket.StompChatController;
import com.ssafy.backend.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class StompInterceptor implements ChannelInterceptor {

    @Autowired
    private RedisService redisService;
    @Autowired
    private StompChatController stompChatController;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 구독 요청
            String destination = Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidBroadcastId");
            String[] arr = destination.split("/");
            String[] dest = arr[arr.length-1].split("\\.");
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            redisService.setValue(sessionId, dest[1]);
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            String broadcastId = redisService.getValue(sessionId);
            // 채팅방 인원 수 -1
            if (broadcastId != null) stompChatController.leave(Integer.parseInt(broadcastId));
            redisService.delete(sessionId);
        }

        return message;
    }
}
