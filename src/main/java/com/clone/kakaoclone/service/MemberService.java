package com.clone.kakaoclone.service;

import com.clone.kakaoclone.dto.TokenDto;
import com.clone.kakaoclone.dto.request.LoginRequestDto;
import com.clone.kakaoclone.dto.request.MemberRequestDto;
import com.clone.kakaoclone.dto.request.ProfileRequestDto;
import com.clone.kakaoclone.dto.response.MemberInfoResponseDto;
import com.clone.kakaoclone.dto.response.MemberResponseDto;
import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.jwt.TokenProvider;
import com.clone.kakaoclone.repository.MemberRepository;
import com.clone.kakaoclone.shared.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;


    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {

        Member member = Member.builder()
                .username(requestDto.getUsername())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .imgUrl(requestDto.getImgUrl())
                .authority(Authority.ROLE_MEMBER)
                .build();

        memberRepository.save(member);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMember_id())
                        .username(member.getUsername())
                        .nickname(member.getNickname())
                        .imgUrl(member.getImgUrl())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getUsername());
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "???????????? ?????? ??? ????????????.");
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_MEMBER", "???????????? ?????? ??? ????????????.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getMember_id())
                        .nickname(member.getNickname())
                        .username(member.getUsername())
                        .imgUrl(member.getImgUrl())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public Member isPresentNickname(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }


    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }


    public ResponseDto<?> checkDupId(String username) {
        if (null != isPresentMember(username)) {
            return ResponseDto.fail("DUPLICATED_USERNAME",
                    "????????? ????????? ?????????.");
        }
        return ResponseDto.success("??????????????? ????????? ?????????.");
    }


    public ResponseDto<?> checkDupNickname(String nickname) {
        if (null != isPresentNickname(nickname)) {
            return ResponseDto.fail("DUPLICATED_NICKNAME","????????? ????????? ?????????.");
        }
        return ResponseDto.success("??????????????? ????????? ?????????.");
    }


    public ResponseDto<?> viewProfile(Long member_id) {
        Optional<Member> optionalMember = memberRepository.findById(member_id);

        Member member = optionalMember.orElse(null);

        if (null != member){
            MemberResponseDto response = new MemberResponseDto(member);
            return ResponseDto.success(response);
        }

        return ResponseDto.fail("NOT_FOUND","?????? ???????????? ????????????");
    }


    public ResponseDto<?> editProfile(Long memberId, ProfileRequestDto profileRequestDto) {
        Optional<Member> optionalmember = memberRepository.findById(memberId);

        if(optionalmember.isEmpty()){
            return ResponseDto.fail("NOT_FOUND_ID","?????? ???????????? ????????????.");
        }

        if (null != isPresentNickname(profileRequestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME","????????? ????????? ?????????.");
        }

        Member member = optionalmember.get();
        member.editProfile(profileRequestDto);
        MemberResponseDto responseDto = new MemberResponseDto(member);

        return ResponseDto.success(responseDto);
    }

    public ResponseDto<?> LoginInfo(UserDetails userInfo) {
        Member member = memberRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("????????? ????????? ????????????.")
        );

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto(member);

        return ResponseDto.success(memberInfoResponseDto);

    }
}
