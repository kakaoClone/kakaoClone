package com.clone.kakaoclone.websocket;

import com.clone.kakaoclone.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
//pub/sub방식 사용시 구독자 관리및 메시지발송이  자동으로 진행되어 세션관리및 클라에게 일일히 메시지 발송 필요 없어짐
@Getter
@Setter
public class ChatRoomDto {
    private String roomId;
    private String name;
//    private Set<WebSocketSession> sessions = new HashSet<>();

//    @Builder
//    public ChatRoomDto(String roomId, String name) {
//        this.roomId = roomId;
//        this.name = name;
//    }

//    public void handleActions(WebSocketSession session, ChatMessageDto chatMessageDto, ChatService chatService) {
//        if (chatMessageDto.getType().equals(ChatMessageDto.MessageType.ENTER)) {
//            sessions.add(session);
//            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장했습니다.");
//        }
//        sendMessage(chatMessageDto, chatService);
//    }
//
//    public <T> void sendMessage(T message, ChatService chatService) {
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));


    public static ChatRoomDto create(String name) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.name = name;
        return chatRoomDto;
    }
}

