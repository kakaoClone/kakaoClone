package com.clone.kakaoclone.dto.request;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String imgUrl;
}
