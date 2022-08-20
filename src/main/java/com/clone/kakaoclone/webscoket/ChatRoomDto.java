package com.clone.kakaoclone.webscoket;

import com.clone.kakaoclone.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
//채팅방 구현을 위한 dto 채팅방은 입장한 클라의 정보를 가지고 있어야하므로 WebSocketSession정보 리스트를 맴버필드로 가짐
//나머지는 채팅방id와 채팅방 이름
//채팅방에서는 입장,대화하기등의 기능이 있어 handleAction을 통해 분기처리
//입장시 채팅방의 세션 정보에 클라의 세션리스트를 추가 채팅방에 메시지가 도착할 경우 채팅룸의 모든 세션에 메시지 발송
public class ChatRoomDto implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String name;


    //pub-sub 방식으로 인해 코드의 간결화
    //1. 구독자 관리의 자동화로 웹소켓 세션 관리가 필요 없이짐
    //2. 발송의 구현도 알서 해결되므로 클라에게 메시지를 발송하는 구현이 필요 없어집니다.
    public static ChatRoomDto create(String name) {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.name = name;
        return chatRoomDto;
    }
}
