package com.clone.kakaoclone.entity;

import com.clone.kakaoclone.dto.request.ProfileRequestDto;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String imgUrl;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "friendId")
//    private Member friend;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @Column
//    private List<Member> friendList = new ArrayList<>();
//
//    @ManyToMany
//    private List<ChatRoom> chatRooms = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Member member = (Member) o;
        return member_id != null && Objects.equals(member_id, member.getMember_id());
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public void editProfile(ProfileRequestDto requestDto){
        this.nickname = requestDto.getNickname();
        this.imgUrl = requestDto.getImgUrl();
    }
}
