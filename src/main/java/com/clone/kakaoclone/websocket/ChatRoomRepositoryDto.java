package com.clone.kakaoclone.websocket;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
//ChatService의 역할을 대체 ChatService 삭제
@Repository
public class ChatRoomRepositoryDto {

    private Map<String, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init() {
        chatRoomDtoMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List chatRoomDtos = new ArrayList<>(chatRoomDtoMap.values());
        Collections.reverse(chatRoomDtos);
        return chatRoomDtos;
    }

    public ChatRoomDto findRoomById(String id) {
        return chatRoomDtoMap.get(id);
    }

    public ChatRoomDto createChatRoom(String name) {
        ChatRoomDto chatRoomDto = ChatRoomDto.create(name);
        chatRoomDtoMap.put(chatRoomDto.getRoomId(), chatRoomDto);
        return chatRoomDto;
    }
}