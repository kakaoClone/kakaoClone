package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.response.ChatRoomResponseDto;
import com.clone.kakaoclone.entity.UserDetailsImpl;
import com.clone.kakaoclone.dto.request.ChatRoomRequestDto;
import com.clone.kakaoclone.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatRooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("") // 빈 채널 생성
    public void createEmptyChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        chatRoomService.createEmptyChatRoom(chatRoomRequestDto, userDetails);
    }

    @PostMapping("/friend/{friendId}") // 친구와 1:1 채널 생성
    public void createChatRoomWithFriend(@PathVariable Long friendId, @RequestBody ChatRoomRequestDto chatRoomRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        chatRoomService.createChatRoomWithFriend(friendId, chatRoomRequestDto, userDetails);
    }

    @PostMapping("/{chatRoomId}/friend/{friendId}") // 채널에 친구초대
    public void inviteFriend(@PathVariable Long chatRoomId, @PathVariable Long friendId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        chatRoomService.inviteFriend(chatRoomId, friendId, userDetails);
    }

    @GetMapping("")// 모든 채팅방
    public List<ChatRoomResponseDto> readAllChatRoom(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatRoomService.readAllChatRoom(userDetails);
    }
}
