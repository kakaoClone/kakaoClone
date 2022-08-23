package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.response.FriendResponseDto;
import com.clone.kakaoclone.entity.UserDetailsImpl;
import com.clone.kakaoclone.service.AdditionalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class AdditionalMemberController {

    private final AdditionalMemberService additionalMemberService;

    @GetMapping("") // 전체 유저 검색
    public List<FriendResponseDto> searchAllUser(@RequestParam String nickname) {
        return additionalMemberService.searchAllUser(nickname);
    }

    @PostMapping("/friends/{friendId}") // id로 친구 추가
    public void addFriendId(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        additionalMemberService.addFriend(friendId, userDetails);
    }

    @PostMapping("/friendsAdd/{friendUsername}") // username 으로 친구 추가
    public void addFriendUsername(@PathVariable String friendUsername, @AuthenticationPrincipal UserDetails userDetails) {
        additionalMemberService.addFriendUsername(friendUsername, userDetails);
    }

    @GetMapping("/searchFriends") // 친구 닉네임 검색
    public List<FriendResponseDto> searchFriend(@RequestParam String nickname,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return additionalMemberService.searchFriend(nickname, userDetails);
    }

    @GetMapping("/friends") // 친구 목록 조회
    public List<FriendResponseDto> readAllFriend(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return additionalMemberService.readAllFriend(userDetails);
    }
}

//    @GetMapping("/{memberId}")
//    public MemberResponseDto memberProfile(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return memberService.memberProfile(memberId, userDetails);
//    }
//
//    @PutMapping("")
//    public void updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto,
//                              @AuthenticationPrincipal UserDetailsImpl userDetails){
//        memberService.updateProfile(profileUpdateRequestDto, userDetails);
//    }
//}
