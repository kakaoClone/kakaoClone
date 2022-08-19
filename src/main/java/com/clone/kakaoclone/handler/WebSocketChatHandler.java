package com.clone.kakaoclone.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
//socket통신은 서버와 클라가 1:n 관계로 여러 클라가 발송한 메시지를 받아 처리할 Handler필요
//TextWebSocketHandler 상속받아 Handler을 작성
@Slf4j //전체적인 app의 흐름이나 error도 확인할 수 있다. [논리적 에러, 타이밍 에러(multi-thread) 등]
@Component // 빈의 아이디 추가?
public class WebSocketChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        //Session이란 고객이 접속했었다는 정보
        String payload = message.getPayload();
        //payload는 전송되는 데이터
        log.info("payload {}", payload);
        TextMessage textMessage = new TextMessage("Welcome chatting sever");
        session.sendMessage(textMessage);
    }
}
