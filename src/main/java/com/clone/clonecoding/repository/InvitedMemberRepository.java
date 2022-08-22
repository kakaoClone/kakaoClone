package com.clone.clonecoding.repository;

import com.clone.clonecoding.entity.Channel;
import com.clone.clonecoding.entity.InvitedMember;
import com.clone.clonecoding.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitedMemberRepository extends JpaRepository<InvitedMember, Long> {
    boolean existsByMemberAndChannel(Member member, Channel channel);
    List<InvitedMember> findAllByMember(Member member);
    List<InvitedMember> findAllByChannel(Channel channel);
}
