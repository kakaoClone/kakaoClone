package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.entity.ChatMessage;
import com.clone.kakaoclone.entity.ChatRoom;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.entity.UserDetailsImpl;
import com.clone.kakaoclone.jwt.TokenProvider;
import com.clone.kakaoclone.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatMessageService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TokenProvider tokenProvider;

    public MessageResponseDto addMessage(MessageRequestDto messageRequestDto, Long roomId, String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));
        Member member = userDetails.getMember();

        ChatMessage chatMessage = ChatMessage.builder()
                .content(messageRequestDto.getContent())
                .member(member)
                .chatRoom(chatRoom)
                .build();

        messageRepository.save(chatMessage);

        return MessageResponseDto.builder()
                .roomId(chatRoom.getId())
                .content(messageRequestDto.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .memberId(member.getMember_id())
                .nickname(member.getNickname())
                .imgUrl(member.getImgUrl())
                .build();
    }

    public List<MessageResponseDto> messages(Long roomId) {
        return messageRepository.findTop100ByChatRoomIdOrderByCreatedAtDesc(roomId).stream().map(MessageResponseDto::new)
                .collect(Collectors.toList());
    }
}
