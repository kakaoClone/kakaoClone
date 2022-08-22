package com.clone.clonecoding.service;

import com.clone.clonecoding.dto.request.LoginDto;
import com.clone.clonecoding.dto.request.MemberRequestDto;
import com.clone.clonecoding.dto.request.ProfileUpdateRequestDto;
import com.clone.clonecoding.dto.response.FriendResponseDto;
import com.clone.clonecoding.dto.response.MemberResponseDto;
import com.clone.clonecoding.dto.response.TokenDto;
import com.clone.clonecoding.entity.Friend;
import com.clone.clonecoding.entity.Member;
import com.clone.clonecoding.repository.FriendRepository;
import com.clone.clonecoding.repository.MemberRepository;
import com.clone.clonecoding.security.TokenProvider;
import com.clone.clonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void signup(MemberRequestDto memberRequestDto) {
        memberRepository.save(Member.builder()
                .username(memberRequestDto.getUsername())
                .nickname(memberRequestDto.getNickname())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .imgUrl(memberRequestDto.getImgUrl())
                .build());
    }

    @Transactional
    public void login(LoginDto loginDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    }

    @Transactional(readOnly = true) // 모든 유저 검색
    public List<FriendResponseDto> searchAllUser(String nickname) {
        List<Member> searchResult = memberRepository.findAllByNicknameContaining(nickname);
        return searchResult.stream().map(FriendResponseDto::new).collect(Collectors.toList());
    }

    @Transactional // 친구 등록
    public void addFriend(Long friendId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        if (member.getId().equals(friendId)) {
            throw new IllegalArgumentException("자신은 친구로 등록할 수 없습니다.");
        }
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if (friendRepository.existsByMemberAndFriend(member, friend)) {
            throw new IllegalArgumentException("이미 등록된 친구입니다.");
        }
        friendRepository.save(Friend.builder()
                .member(member)
                .friend(friend)
                .build());
    }

    @Transactional(readOnly = true) // 친구 닉네임 검색
    public List<FriendResponseDto> searchFriend(String nickname, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<Friend> friends = friendRepository.findAllByMember(member);
        List<Member> friendList = friends.stream().map(Friend::getFriend).collect(Collectors.toList());
        List<Member> searchResult = memberRepository.findAllByNicknameContaining(nickname);
        List<Member> friendSearchList = new ArrayList<>();
        for (Member friend : friendList) {
            if (searchResult.contains(friend)) {
                friendSearchList.add(friend);
            }
        }
        return friendSearchList.stream().map(FriendResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true) // 친구목록 조회
    public List<FriendResponseDto> readAllFriend(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<Friend> friends = friendRepository.findAllByMember(member);
        return friends.stream().map(Friend::getFriend).map(FriendResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponseDto memberProfile(Long memberId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Member someone = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));
        if (member.getId().equals(someone.getId())) {
            return MemberResponseDto.builder()
                    .id(someone.getId())
                    .nickname(someone.getNickname())
                    .imgUrl(someone.getImgUrl())
                    .isFriend("isMe")
                    .build();
        } else if (friendRepository.existsByMemberAndFriend(member, someone)) {
            return MemberResponseDto.builder()
                    .id(someone.getId())
                    .nickname(someone.getNickname())
                    .imgUrl(someone.getImgUrl())
                    .isFriend("isFriend")
                    .build();
        }
        return MemberResponseDto.builder()
                .id(someone.getId())
                .nickname(someone.getNickname())
                .imgUrl(someone.getImgUrl())
                .isFriend("isNotFriend")
                .build();

    }

    @Transactional
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto, UserDetailsImpl userDetails){
        Member member = userDetails.getMember();
        member.updateProfile(profileUpdateRequestDto.getNickname(), profileUpdateRequestDto.getImgUrl());
        memberRepository.save(member);
    }
}
