package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.request.LoginRequestDto;
import com.clone.kakaoclone.dto.request.MemberRequestDto;
import com.clone.kakaoclone.dto.request.ProfileRequestDto;
import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    //Valid는 유효성 검사
    @PostMapping("/api/members/signup")
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @PostMapping("/api/members/login")
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @GetMapping("/test")
    public ResponseDto<?> test() {
        return ResponseDto.success("성공");
    }

    @GetMapping("/api/members/check/{username}")
    public ResponseDto<?> checkDupId(@PathVariable String username){
        return memberService.checkDupId(username);
    }

    @GetMapping("/api/members/{nickname}")
    public ResponseDto<?> checkDupNickname(@PathVariable String nickname){
        return memberService.checkDupNickname(nickname);

    }
    @GetMapping("/api/members/find/{memberId}")
    public ResponseDto<?> veiwProfile(@PathVariable Long memberId){
        return memberService.viewProfile(memberId);

    }
    @PutMapping("/api/members/find/{memberId}")
    public ResponseDto<?> editProfile(@PathVariable Long memberId, @RequestBody ProfileRequestDto requestDto){

        return memberService.editProfile(memberId, requestDto);
    }

    //회원 정보 조회
    @GetMapping("/api/members/info")
    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetails userInfo) {
        try {
            return  memberService.LoginInfo(userInfo);
        } catch (Exception e) {
            return  ResponseDto.fail("NOT_STATE_LOGIN", e.getMessage());
        }
    }
}
