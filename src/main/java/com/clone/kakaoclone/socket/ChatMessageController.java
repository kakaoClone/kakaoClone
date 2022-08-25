package com.clone.kakaoclone.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public void addMessage(@RequestBody MessageRequestDto messageRequestDto, @DestinationVariable Long roomId,
                           @Header("Authorization") String token) {
        token = token.substring(7);
        MessageResponseDto messageResponseDto = messageService.addMessage(messageRequestDto, roomId, token);
        simpleMessageSendingOperations.convertAndSend("/sub/channel/" + roomId, messageResponseDto);
    }
}
