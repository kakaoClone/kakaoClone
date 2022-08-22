package com.clone.clonecoding.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Channel extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String channelName;

    @OneToMany
    private List<InvitedMember> invitedMembers;

//    @OneToMany
//    private List<Message> messages;

    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
