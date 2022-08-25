package com.clone.kakaoclone.repository;

import com.clone.kakaoclone.entity.ChatRoom;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom,Long>  {
    boolean existsByMemberAndFriend(Member member, Member friend);
    boolean existsByMemberAndChatRoom(Member member, ChatRoom chatRoom);
    List<UserChatRoom> findAllByMember(Member member);
    List<UserChatRoom> findAllByChatRoom(ChatRoom chatRoom);
    List<UserChatRoom> findAllByFriend(Member member);

    UserChatRoom findByMemberAndAndFriend(Member member, Member friend);
}
