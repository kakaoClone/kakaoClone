package com.clone.kakaoclone.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MemberInfoResponseDto {

    private Long id;
    private String username;
    private String nickname;
    private String imgUrl;
    private LocalDateTime createdAt;
}
