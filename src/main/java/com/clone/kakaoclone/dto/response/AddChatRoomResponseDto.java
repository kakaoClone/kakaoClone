package com.clone.kakaoclone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddChatRoomResponseDto {
    private Long id;
    private String chatRoomName;
    private int memberCnt;
}
