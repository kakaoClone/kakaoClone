package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.dto.response.ResponseDto;
import com.clone.kakaoclone.entity.ChatRoom;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.entity.UserDetailsImpl;
import com.clone.kakaoclone.jwt.TokenProvider;
import com.clone.kakaoclone.repository.ChatRoomRepository;
import com.clone.kakaoclone.repository.MemberRepository;
import com.clone.kakaoclone.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;
    private final MemberRepository memberRepository;

    public MessageDTO addMessage(MessageDTO messageDto, UserDetails userDetails, Long chatroomId) {
        Member member = searchMember(userDetails.getUsername());
        ChatRoom chatRoom = validateRole(chatroomId, userDetails);
        messageRepository.save(Message.builder()
                .message(messageDto.getMessage())
                .member(member)
                .chatRoom(chatRoom)
                .build());
        messageDto.setMemberId(member.getMember_id());
        messageDto.setUsername(member.getUsername());
        messageDto.setNickname(member.getNickname());
        messageDto.setIconUrl(member.getImgUrl());
        return messageDto;
    }

    public ResponseDto<MessageDTO> messages(Long channelId, UserDetailsImpl userDetails) {
        validateRole(channelId, userDetails);
        return new ResponseDto<>(true, messageRepository.findTop100ByChatRoomIdOrderByCreatedAtDesc(channelId).stream().map(MessageDTO::new).collect(Collectors.toList()));
    }

    private ChatRoom validateRole(Long chatroomId, UserDetails userDetails) throws IllegalArgumentException {
        Member member = searchMember(userDetails.getUsername());
        ChatRoom chatRoom = chatRoomRepository.findById(chatroomId).orElseThrow(() ->
                new IllegalArgumentException("채팅방이 존재하지 않습니다.")
        );
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        if(chatRoom.getIsPrivate()) {
            //비공개 채널일떄 초대됐는지 검사
            if (!userChatRoomRepository.existsByChatRoomAndMember(chatRoom, member)) {
                throw new IllegalArgumentException("채팅 권한이 없습니다.");
            }
        }
        //공개채널일경우 검사 안함
        return chatRoom;
    }


    public Member searchMember(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("로그인이 필요합니다.")
        );
        return  member;
    }



}
