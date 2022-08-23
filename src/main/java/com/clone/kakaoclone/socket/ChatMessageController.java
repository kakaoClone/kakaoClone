package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessageSendingOperations simpleMessageSendingOperations;
    private final ChatMessageService messageService;

    @ResponseBody
    @GetMapping("/api/room/{roomId}")
    public List<MessageResponseDto> messages(@PathVariable Long roomId) {

        return messageService.messages(roomId);
    }

    @MessageMapping(value = {"/message/{roomId}"})
    public void addMessage(@RequestBody MessageRequestDto messageRequestDto, @DestinationVariable Long roomId) {

        MessageResponseDto messageResponseDto = messageService.addMessage(messageRequestDto, roomId);
        simpleMessageSendingOperations.convertAndSend("/sub/channel/" + roomId, messageResponseDto);
    }
}
