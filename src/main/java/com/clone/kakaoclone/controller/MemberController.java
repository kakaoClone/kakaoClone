//package com.clone.kakaoclone.controller;
//
//import com.clone.kakaoclone.dto.request.MemberRequestDto;
//import com.clone.kakaoclone.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class MemberController {
//
//    private final MemberService memberService;
//
//    @PostMapping("/members/signup")
//    public void signup(@RequestBody MemberRequestDto memberRequestDto){
//        memberService.signup(memberRequestDto);
//    }
//}
