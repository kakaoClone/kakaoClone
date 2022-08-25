package com.clone.kakaoclone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String nickname;
    private String imgUrl;
    private String isFriend;
}
