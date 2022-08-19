package com.clone.kakaoclone.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String imgUrl;

    @OneToMany(mappedBy = "friend")
    List<Member> friendList = new ArrayList<>();

    @ManyToMany
    List<ChatRoom> chatRooms = new ArrayList<>();
}
