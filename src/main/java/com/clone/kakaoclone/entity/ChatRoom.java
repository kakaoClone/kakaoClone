package com.clone.kakaoclone.entity;

import com.clone.kakaoclone.socket.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Getter
public class ChatRoom extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

//    @Column(nullable = false)
//    private Boolean isPrivate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private List<UserChatRoom> userChatRoomList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private List<Message> messages;//초대된 유저
}
