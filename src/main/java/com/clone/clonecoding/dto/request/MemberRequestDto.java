package com.clone.clonecoding.dto.request;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String imgUrl;
}
