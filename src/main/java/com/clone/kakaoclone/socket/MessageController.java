package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    @ResponseBody
    @GetMapping("/api/chat/{channelId}")
    public ResponseEntity<?> messages(@PathVariable Long channelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok().body(messageService.messages(channelId, userDetails));
    }

    @MessageMapping(value = {"/message","/message/{channelId}"})
    public void addMessage(MessageDTO messageDto, @AuthenticationPrincipal UserDetails userDetails, @DestinationVariable Long channelId) {
        // channelId=messageDto.getChannelId();
        System.out.println(channelId);
        MessageDTO responseMessageDto = messageService.addMessage(messageDto, userDetails, channelId);
        template.convertAndSend("/sub/channel/" + channelId, responseMessageDto);
    }
}
