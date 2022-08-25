package com.clone.kakaoclone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRoomResponseDto {
    private Long id;
    private String chatRoomName;
    private int memberCnt;
    private LocalDateTime lastChatTime;
    private String lastContent;

}
