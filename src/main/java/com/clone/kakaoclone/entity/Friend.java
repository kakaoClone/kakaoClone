package com.clone.kakaoclone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 자신의 정보
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    /*// 친구가 등록한 것
    @JoinColumn(name = "to_member_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member toMember;*/
// 내가 등록한 것
    @JoinColumn(name = "from_member_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member fromMember;

}
