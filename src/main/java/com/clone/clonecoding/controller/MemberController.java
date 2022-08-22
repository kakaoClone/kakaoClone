package com.clone.clonecoding.controller;

import com.clone.clonecoding.dto.request.LoginDto;
import com.clone.clonecoding.dto.request.MemberRequestDto;
import com.clone.clonecoding.dto.request.ProfileUpdateRequestDto;
import com.clone.clonecoding.dto.response.FriendResponseDto;
import com.clone.clonecoding.dto.response.MemberResponseDto;
import com.clone.clonecoding.security.UserDetailsImpl;
import com.clone.clonecoding.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup(@RequestBody MemberRequestDto memberRequestDto){
        memberService.signup(memberRequestDto);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDto loginDto, HttpServletResponse response){
        memberService.login(loginDto, response);
    }

    @GetMapping("") // 전체 유저 검색
    public List<FriendResponseDto> searchAllUser(@RequestParam String nickname){
        return memberService.searchAllUser(nickname);
    }

    @PostMapping("/friends/{friendId}") // 친구 추가
    public void addFriend(@PathVariable Long friendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.addFriend(friendId, userDetails);
    }

    @GetMapping("/searchFriends") // 친구 닉네임 검색
    public List<FriendResponseDto> searchFriend(@RequestParam String nickname,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.searchFriend(nickname, userDetails);
    }

    @GetMapping("/friends") // 친구 목록 조회
    public List<FriendResponseDto> readAllFriend(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.readAllFriend(userDetails);
    }

    @GetMapping("/{memberId}")
    public MemberResponseDto memberProfile(@PathVariable Long memberId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memberService.memberProfile(memberId, userDetails);
    }

    @PutMapping("")
    public void updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        memberService.updateProfile(profileUpdateRequestDto, userDetails);
    }
}
