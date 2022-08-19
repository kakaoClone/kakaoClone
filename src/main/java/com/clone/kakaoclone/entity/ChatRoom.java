package com.clone.kakaoclone.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatName;

    @ManyToMany
    @JoinColumn(nullable = false)
    List<Member> memberList;
}
