package com.clone.kakaoclone.socket;

import com.clone.kakaoclone.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop100ByChatRoomIdOrderByCreatedAtDesc(Long roomId);
    ChatMessage findTopByChatRoomIdOrderByCreatedAtDesc(Long roomId);

}
