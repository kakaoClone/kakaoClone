package com.clone.kakaoclone.webscoket;

import com.clone.kakaoclone.entity.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
@Repository
//채팅방을 생성 및 정보를 조회함
//ChatService의 역할을 대신함
public class ChatRoomRepository {
    private Map<String, ChatRoomDto> chatRoomDtos;

    @PostConstruct
    private void init() {
        chatRoomDtos = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List ChatRoomDto = new ArrayList<>(chatRoomDtos.values());
        Collections.reverse(ChatRoomDto);
        return ChatRoomDto;
    }

    public ChatRoomDto findRoomById(String id) {
        return chatRoomDtos.get(id);
    }

    public ChatRoomDto createChatRoom(String name) {
        ChatRoomDto chatRoomDto = ChatRoomDto.create(name);
        chatRoomDtos.put(chatRoomDto.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }

}
