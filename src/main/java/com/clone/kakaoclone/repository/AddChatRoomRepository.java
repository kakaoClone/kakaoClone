package com.clone.kakaoclone.repository;

import com.clone.kakaoclone.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
