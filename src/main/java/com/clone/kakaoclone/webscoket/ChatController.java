package com.clone.kakaoclone.webscoket;

//import com.clone.kakaoclone.entity.ChatMessage;
//import com.clone.kakaoclone.webscoket.ChatRoomDto;
//import com.clone.kakaoclone.webscoket.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//기존의 WebSocketChatHandler이 했던 역할을 대체

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
//채팅방 생성및 조회는 rest api로 진행
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

//JOIN대신 ENTER인입
    @MessageMapping("/chat/message")
    //MessageMapping를 통해 웹소켓으로 들어오는메시지 발행 처리
    public void message(ChatMessageDto chatMessageDto) {
        if (ChatMessageDto.MessageType.ENTER.equals(chatMessageDto.getType()))
            chatRoomRepository.enterChatRoomDto(chatMessageDto.getRoomId());
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장하셨습니다.");
        redisPublisher.publish(chatRoomRepository.getTopic(chatMessageDto.getRoomId()),chatMessageDto);
    }
}
