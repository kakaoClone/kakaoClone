package com.clone.kakaoclone.dto.request;

import com.clone.kakaoclone.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
//채팅방 구현을 위한 dto 채팅방은 입장한 클라의 정보를 가지고 있어야하므로 WebSocketSession정보 리스트를 맴버필드로 가짐
//나머지는 채팅방id와 채팅방 이름
//채팅방에서는 입장,대화하기등의 기능이 있어 handleAction을 통해 분기처리
//입장시 채팅방의 세션 정보에 클라의 세션리스트를 추가 채팅방에 메시지가 도착할 경우 채팅룸의 모든 세션에 메시지 발송
public class ChatRoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoomDto(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }
    public void handleActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService){
        if(chatMessageDto.getType().equals(ChatMessageDto.MessageType.ENTER)){
            sessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessageDto,chatService);
    }
    public <T> void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
    }
}
