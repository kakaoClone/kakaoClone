package com.clone.kakaoclone.service;

import com.clone.kakaoclone.dto.request.ChatRoomDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDto> chatRoomDtos;

    @PostConstruct
    private void init(){
        chatRoomDtos = new LinkedHashMap<>();
    }
    public List<ChatRoomDto> findAllRoom(){
        return new ArrayList<>(chatRoomDtos.values());
    }
    public ChatRoomDto findRoomById(String roomId){
        return chatRoomDtos.get(roomId);
    }
    public ChatRoomDto creatRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRoomDtos.put(randomId, chatRoomDto);
        return  chatRoomDto;
    }
    public <T> void  sendMessage(WebSocketSession session,T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }

}
