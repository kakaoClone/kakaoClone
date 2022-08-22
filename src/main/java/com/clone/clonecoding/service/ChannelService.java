package com.clone.clonecoding.service;

import com.clone.clonecoding.dto.request.ChannelRequestDto;
import com.clone.clonecoding.dto.response.ChannelResponseDto;
import com.clone.clonecoding.entity.Channel;
import com.clone.clonecoding.entity.InvitedMember;
import com.clone.clonecoding.entity.Member;
import com.clone.clonecoding.repository.ChannelRepository;
import com.clone.clonecoding.repository.FriendRepository;
import com.clone.clonecoding.repository.InvitedMemberRepository;
import com.clone.clonecoding.repository.MemberRepository;
import com.clone.clonecoding.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final InvitedMemberRepository invitedMemberRepository;
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    @Transactional // 빈 채널 생성
    public void createEmptyChannel(ChannelRequestDto channelRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Channel channel = Channel.builder()
                .channelName(channelRequestDto.getChannelName())
                .build();
        channelRepository.save(channel);
        invitedMemberRepository.save(InvitedMember.builder()
                .member(member)
                .channel(channel)
                .build());
    }

    @Transactional // 친구와 1:1 채널 생성
    public void createChannelWithFriend(Long friendId, ChannelRequestDto channelRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if(!friendRepository.existsByMemberAndFriend(member, friend)){
            throw new IllegalArgumentException("친구만 초대할 수 있습니다.");
        }
        Channel channel = Channel.builder()
                .channelName(channelRequestDto.getChannelName())
                .build();
        channelRepository.save(channel);
        invitedMemberRepository.save(InvitedMember.builder()
                .member(member)
                .channel(channel)
                .build());
        invitedMemberRepository.save(InvitedMember.builder()
                .member(friend)
                .channel(channel)
                .build());
    }

    @Transactional // 채널에 친구 초대
    public void inviteFriend(Long channelId, Long friendId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다"));
        if (!invitedMemberRepository.existsByMemberAndChannel(member, channel)) {
            throw new IllegalArgumentException("채널에 있는 사람만 초대권한이 있습니다.");
        }
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if (invitedMemberRepository.existsByMemberAndChannel(friend, channel)) {
            throw new IllegalArgumentException("이미 채널에 존재하는 친구입니다.");
        }
        if(!friendRepository.existsByMemberAndFriend(member, friend)){
            throw new IllegalArgumentException("친구만 초대할 수 있습니다.");
        }
        invitedMemberRepository.save(InvitedMember.builder()
                .member(friend)
                .channel(channel)
                .build());
    }

    @Transactional(readOnly = true) // 참가한 채널 모두 조회
    public List<ChannelResponseDto> readAllChannel(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<InvitedMember> invitedMembers = invitedMemberRepository.findAllByMember(member);
        List<Channel> channels = invitedMembers.stream().map(InvitedMember::getChannel).collect(Collectors.toList());
        List<ChannelResponseDto> result = new ArrayList<>();
        for (Channel channel : channels) {
            result.add(ChannelResponseDto.builder()
                    .id(channel.getId())
                    .channelName(channel.getChannelName())
                    .memberCnt(invitedMemberRepository.findAllByChannel(channel).size())
                    .build());
        }
        return  result;
    }
}
