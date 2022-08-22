package com.clone.kakaoclone.repository;

import com.clone.kakaoclone.entity.Friend;
import com.clone.kakaoclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByMemberAndFromMember(Member member, Member friend);
    List<Friend> findAllByMember(Member member);
}
