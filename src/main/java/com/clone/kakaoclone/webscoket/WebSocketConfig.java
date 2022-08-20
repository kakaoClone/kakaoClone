package com.clone.kakaoclone.webscoket;

import com.clone.kakaoclone.webscoket.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket// 해당 어노테이션으로 WebSocket활성화
//먼저 만든 handler을 이용하여 Websocket을 활성화하기위한 config파일 작성
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketChatHandler, "/ws/chat").setAllowedOrigins("*");
        //WebSocket에서 접속하기위한 endpoint는 /ws/chat로 설정
        //설정 도메인이 다른 서버에서도 접속 가능하도록 cors : .setAllowedOrigins("*");설정 추가
    }
}
