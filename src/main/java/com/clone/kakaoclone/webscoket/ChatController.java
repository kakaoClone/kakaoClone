package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.request.ChatRoomDto;
import com.clone.kakaoclone.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
//채팅방 생성및 조회는 rest api로 진행
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoomDto chatRoom(@RequestParam String name){
        return chatService.creatRoom(name);
    }
    @GetMapping
    public List<ChatRoomDto> findAllRoom(){
        return chatService.findAllRoom();
    }
}