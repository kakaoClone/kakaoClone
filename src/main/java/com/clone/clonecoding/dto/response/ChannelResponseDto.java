package com.clone.clonecoding.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChannelResponseDto {
    private Long id;
    private String channelName;
    private int memberCnt;
}
