package com.clone.clonecoding.repository;

import com.clone.clonecoding.entity.Friend;
import com.clone.clonecoding.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    boolean existsByMemberAndFriend(Member member, Member friend);
    List<Friend> findAllByMember(Member member);
}
