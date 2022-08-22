package com.clone.kakaoclone.dto.response;

import com.clone.kakaoclone.entity.Member;
import lombok.Getter;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final String nickname;
    private final String imgUrl;

    public FriendResponseDto(Member member){
        this.id = member.getMember_id();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
    }
}
