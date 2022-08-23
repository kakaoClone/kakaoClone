package com.clone.kakaoclone.socket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {

    private Long channelId;
    private String message;
    private LocalDateTime createdAt;
    private Long MemberId;
    private String username;
    private String nickname;
    private String iconUrl;

    public MessageDTO(Message message) {
        this.channelId = message.getChatRoom().getId();
        this.message = message.getMessage();
        this.createdAt = message.getCreatedAt();
        this.MemberId = message.getMember().getMember_id();
        this.username = message.getMember().getUsername();
        this.nickname = message.getMember().getNickname();
        this.iconUrl = message.getMember().getImgUrl();
    }
}
