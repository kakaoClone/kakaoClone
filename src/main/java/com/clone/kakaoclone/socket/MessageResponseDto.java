package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MessageResponseDto {

    private Long roomId;
    private String content;
    private LocalDateTime createdAt;
    private Long memberId;
    private String nickname;
    private String imgUrl;

    public MessageResponseDto(ChatMessage chatMessage){
        this.roomId = chatMessage.getChatRoom().getId();
        this.content = chatMessage.getContent();
        this.createdAt = chatMessage.getCreatedAt();
        this.memberId = chatMessage.getMember().getMember_id();
        this.nickname = chatMessage.getMember().getNickname();
        this.imgUrl = chatMessage.getMember().getImgUrl();
    }
}
