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
    private LocalDateTime modifiedAt;

    public MemberResponseDto(Member member){
        this.id = member.getMember_id();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.imgUrl = member.getImgUrl();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }

}
