package com.clone.kakaoclone.controller;

import com.clone.kakaoclone.dto.request.LoginRequestDto;
import com.clone.kakaoclone.dto.request.MemberRequestDto;
import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.service.MemberService;
import lombok.RequiredArgsConstructor;
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
                                HttpServletResponse response
    ) {
        return memberService.login(requestDto, response);
    }

    @GetMapping("/test")
    public String test() {
        return "테스트 성공";
    }
}
