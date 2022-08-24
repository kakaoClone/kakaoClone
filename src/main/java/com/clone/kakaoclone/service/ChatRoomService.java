package com.clone.kakaoclone.service;

import com.clone.kakaoclone.dto.request.ChatRoomRequestDto;
import com.clone.kakaoclone.dto.response.ChatRoomResponseDto;
import com.clone.kakaoclone.dto.response.CreatedRoomResponseDto;
import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.entity.*;
import com.clone.kakaoclone.repository.ChatRoomRepository;
import com.clone.kakaoclone.repository.FriendRepository;
import com.clone.kakaoclone.repository.MemberRepository;
import com.clone.kakaoclone.repository.UserChatRoomRepository;
import com.clone.kakaoclone.socket.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    //추가
    private final ChatMessageRepository chatMessageRepository;

    @Transactional // 빈 채널 생성
    public void createEmptyChatRoom(ChatRoomRequestDto chatRoomRequestDto, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        ChatRoom chatRoom = ChatRoom.builder()
                .chatName(chatRoomRequestDto.getChatRoomName())
                .build();
        chatRoomRepository.save(chatRoom);
        userChatRoomRepository.save(UserChatRoom.builder()
                .member(member)
                .chatRoom(chatRoom)
                .build());
    }

    @Transactional // 친구와 1:1 채널 생성
    public ResponseDto<?> createChatRoomWithFriend(Long friendId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if(!friendRepository.existsByMemberAndFromMember(member, friend)){
            //throw new IllegalArgumentException("친구만 초대할 수 있습니다.");
            return ResponseDto.fail("JUST_FRIEND_INVITE", "친구만 초대할 수 있습니다.");
        }if(userChatRoomRepository.existsByMemberAndFriend(member, friend)||userChatRoomRepository.existsByMemberAndFriend(friend,member)){
            //throw new IllegalArgumentException("이미 생성된 채팅방입니다.");
            return ResponseDto.fail("ALEADY_CREATE_CHATROOM", "이미 생성된 채팅방입니다.");
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .chatName(friend.getNickname())
                .build();
        chatRoomRepository.save(chatRoom);
        UserChatRoom userChatRoom = userChatRoomRepository.save(UserChatRoom.builder()
                .member(member)
                .friend(friend)
                .chatRoom(chatRoom)
                .build());
//        userChatRoomRepository.save(UserChatRoom.builder()
//                .member(friend)
//                .chatRoom(chatRoom)
//                .build());
        CreatedRoomResponseDto createdRoomResponseDto = CreatedRoomResponseDto.builder()
                .friendNick(friend.getNickname())
                .build();
        return ResponseDto.success(createdRoomResponseDto);
    }

    @Transactional // 채널에 친구 초대
    public void inviteFriend(Long chatRoomId, Long friendId, UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다"));
        if (!userChatRoomRepository.existsByMemberAndChatRoom(member, chatRoom)) {
            throw new IllegalArgumentException("채널에 있는 사람만 초대권한이 있습니다.");
        }
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 친구가 존재하지 않습니다."));
        if (userChatRoomRepository.existsByMemberAndChatRoom(friend, chatRoom)) {
            throw new IllegalArgumentException("이미 채널에 존재하는 친구입니다.");
        }
        if(!friendRepository.existsByMemberAndFromMember(member, friend)){
            throw new IllegalArgumentException("친구만 초대할 수 있습니다.");
        }
        userChatRoomRepository.save(UserChatRoom.builder()
                .member(friend)
                .chatRoom(chatRoom)
                .build());
    }

    @Transactional(readOnly = true) // 참가한 채널 모두 조회
    public List<ChatRoomResponseDto> readAllChatRoom(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        List<UserChatRoom> userChatRooms = userChatRoomRepository.findAllByMember(member);
        List<ChatRoom> chatRooms = userChatRooms.stream().map(UserChatRoom::getChatRoom).collect(Collectors.toList());
        List<ChatRoomResponseDto> result = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            result.add(ChatRoomResponseDto.builder()
                    .id(chatRoom.getId())
                    .chatRoomName(chatRoom.getChatName())
                    .memberCnt(userChatRoomRepository.findAllByChatRoom(chatRoom).size())
                    .lastChatTime(chatMessageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoom.getId()).getCreatedAt())
                    .lastContent(chatMessageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoom.getId()).getContent())
                    .build());
        }
        return result;
    }
}
