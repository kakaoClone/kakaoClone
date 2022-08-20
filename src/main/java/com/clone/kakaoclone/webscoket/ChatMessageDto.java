package com.clone.kakaoclone.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//채팅 메시지를 주고 받기 위한 DTO로 상황에 따라 입장, 대화하기를 enum으로 선언
//나머지는 채팅방 구별id, 발신자 ,메시지로 구분
public class ChatMessageDto {
    // 메시지 타입 : 입장 , 채팅
    public enum MessageType{
        ENTER,TALK

    }
    private MessageType type; //메시지 타입
    private String roomId; //방번호
    private String sender;//발신자
    private String message;//메시지
}
