package com.clone.clonecoding.controller;

import com.clone.clonecoding.dto.request.ChannelRequestDto;
import com.clone.clonecoding.dto.response.ChannelResponseDto;
import com.clone.clonecoding.entity.Channel;
import com.clone.clonecoding.security.UserDetailsImpl;
import com.clone.clonecoding.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("") // 빈 채널 생성
    public void createEmptyChannel(@RequestBody ChannelRequestDto channelRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        channelService.createEmptyChannel(channelRequestDto, userDetails);
    }

    @PostMapping("/friend/{friendId}") // 친구와 1:1 채널 생성
    public void createChannelWithFriend(@PathVariable Long friendId, @RequestBody ChannelRequestDto channelRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        channelService.createChannelWithFriend(friendId, channelRequestDto, userDetails);
    }

    @PostMapping("/{channelId}/friend/{friendId}") // 채널에 친구초대
    public void inviteFriend(@PathVariable Long channelId, @PathVariable Long friendId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        channelService.inviteFriend(channelId, friendId, userDetails);
    }

    @GetMapping("")
    public List<ChannelResponseDto> readAllChannel(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return channelService.readAllChannel(userDetails);
    }
}
