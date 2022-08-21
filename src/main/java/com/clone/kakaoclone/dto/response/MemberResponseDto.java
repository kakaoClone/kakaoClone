package com.clone.kakaoclone.dto.response;

import com.clone.kakaoclone.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;

    private String username;

    private String nickname;

    private String imgUrl;



    private LocalDateTime createdAt;

    public MemberResponseDto(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
        this.createdAt = member.getCreatedAt();

    }

    public MemberResponseDto(String username, String nickname, String imgUrl){
        this.username = username;
        this.nickname = nickname;
        this.imgUrl = imgUrl;

    }
}
