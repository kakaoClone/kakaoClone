package com.clone.kakaoclone.socket;


import com.clone.kakaoclone.entity.ChatRoom;
import com.clone.kakaoclone.entity.Member;
import com.clone.kakaoclone.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Message extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private ChatRoom chatRoom;

    @Column
    private String message;

}
