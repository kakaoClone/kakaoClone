package com.clone.kakaoclone.repository;

import com.clone.kakaoclone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByUsername(String user);

    Optional<Member> findByNickname(String nickname);

    List<Member> findAllByNicknameContaining(String nickname);

}
