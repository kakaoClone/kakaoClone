package com.clone.kakaoclone.service;

import com.clone.kakaoclone.dto.response.FriendResponseDto;
import com.clone.kakaoclone.entity.Friend;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.entity.UserDetailsImpl;
import com.clone.kakaoclone.jwt.TokenProvider;
import com.clone.kakaoclone.repository.FriendRepository;
import com.clone.kakaoclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdditionalMemberService {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @Transactional(readOnly = true) // 모든 유저 검색
    public List<FriendResponseDto> searchAllUser(String nickname) {
        List<Member> searchResult = memberRepository.findAllByNicknameContaining(nickname);
        return searchResult.stream().map(FriendResponseDto::new).collect(Collectors.toList());
    }

    @Transactional // 친구 등록
    public void addFriend(Long friendId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        if (member.getMember_id().equals(friendId)) {
            throw new IllegalArgumentException("자신은 친구로 등록할 수 없습니다.");
        }
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if (friendRepository.existsByMemberAndFromMember(member, friend)) {
            throw new IllegalArgumentException("이미 등록된 친구입니다.");
        }
        friendRepository.save(Friend.builder()
                .member(member)
                .fromMember(friend)
                .build());
    }

    @Transactional(readOnly = true) // 친구 닉네임 검색
    public List<FriendResponseDto> searchFriend(String nickname, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<Friend> friends = friendRepository.findAllByMember(member);
        List<Member> friendList = friends.stream().map(Friend::getFromMember).collect(Collectors.toList());
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
        return friends.stream().map(Friend::getFromMember).map(FriendResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void addFriendUsername(String friendUsername, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        if (member.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("자신은 친구로 등록할 수 없습니다.");
        }
        Member friend = memberRepository.findByUsername(friendUsername)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if (friendRepository.existsByMemberAndFromMember(member, friend)) {
            throw new IllegalArgumentException("이미 등록된 친구입니다.");
        }
        friendRepository.save(Friend.builder()
                .member(member)
                .fromMember(friend)
                .build());
    }
}