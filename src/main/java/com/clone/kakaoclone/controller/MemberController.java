package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.request.LoginRequestDto;
import com.clone.kakaoclone.dto.request.MemberRequestDto;
import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;


    @RequestMapping(value = "/api/members/signup", method = RequestMethod.POST)
    public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
        return memberService.createMember(requestDto);
    }

    @RequestMapping(value = "/api/members/login", method = RequestMethod.POST)
    public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                                HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }
    //물어보기
    @GetMapping("/api/members/check/{username}")
    public ResponseDto<?> LoginInfo(@AuthenticationPrincipal UserDetails userInfo){
        try {
            return memberService.LoginInfo(userInfo);
        }catch (Exception e){
            return ResponseDto.fail("NOT_STATE_LOGIN", e.getMessage());
        }

    }
}
