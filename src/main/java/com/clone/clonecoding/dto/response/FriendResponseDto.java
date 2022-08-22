package com.clone.clonecoding.dto.response;

import com.clone.clonecoding.entity.Member;
import lombok.Getter;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final String nickname;
    private final String imgUrl;

    public FriendResponseDto(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
    }
}
