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
//채팅방을 생성 ,조회 , 1개의 세션에 메시지를 발송하는 서비스 구현
//채팅방map는 서버에 생성된 모든 채팅방의 정보를 모아둔 구조체
//채팅방의 정보 저장은 빠른 구현을 위해 hashmap사용
public class ChatService {

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomDto> chatRoomDtos;

    @PostConstruct
    private void init(){
        chatRoomDtos = new LinkedHashMap<>();
    }
    public List<ChatRoomDto> findAllRoom(){
        return new ArrayList<>(chatRoomDtos.values());
        //채팅방 조회- 채팅방 map에담긴 정보를 조회
    }
    public ChatRoomDto findRoomById(String roomId){
        return chatRoomDtos.get(roomId);
    }
    public ChatRoomDto creatRoom(String name){
        //채팅ㅂ아 생성 - Random UUID로 구별 id를 가진 객체 생성 채팅방 map에 추가
        String randomId = UUID.randomUUID().toString();
        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRoomDtos.put(randomId, chatRoomDto);
        return  chatRoomDto;
    }
    public <T> void  sendMessage(WebSocketSession session,T message){
        //메시지 발송 - 지정한 websocket 세션에 메시지를 발송
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }

}
