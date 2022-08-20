package com.clone.kakaoclone.webscoket;

//import com.clone.kakaoclone.webscoket.WebSocketChatHandler;
//import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker// stomp를 사용
//@EnableWebSocket// 해당 어노테이션으로 WebSocket활성화
//먼저 만든 handler을 이용하여 Websocket을 활성화하기위한 config파일 작성
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //pub-sub메시징 구현을 위해 메시지 발행요청은 prefix는 /pub로 설정
        //메시지를 구동하는 요청은 prefix/sub로 설정
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //websocket의 연결주소는 /ws-stomp로 설정
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")
                .withSockJS();
    }
}

