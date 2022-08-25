package com.clone.kakaoclone.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatedRoomResponseDto {
    private String friendNick;
    private Long roomId;
}
