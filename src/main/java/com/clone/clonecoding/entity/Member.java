package com.clone.clonecoding.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@Entity
@Getter
@Builder
@AllArgsConstructor
public class Member {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String imgUrl;

//    @ManyToOne
//    @JoinColumn(name = "FRIEND_ID")
//    private Member friend;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Friend> friendList = new ArrayList<>();

    public void updateProfile(String nickname, String imgUrl){
        this.nickname = nickname;
        this.imgUrl = imgUrl;
    }

//    @OneToMany
//    private List<InvitedMember> invitedMembers;
}
