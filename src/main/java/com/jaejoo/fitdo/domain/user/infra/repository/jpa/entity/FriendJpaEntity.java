package com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class FriendJpaEntity {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserJpaEntity sender;

    @Getter
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserJpaEntity receiver;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    public FriendJpaEntity(UserJpaEntity sender, UserJpaEntity receiver, FriendStatus friendStatus) {
        this.sender = sender;
        this.receiver = receiver;
        this.friendStatus = friendStatus;
    }

    public static FriendJpaEntity apply(UserJpaEntity from, UserJpaEntity to) {
        return new FriendJpaEntity(from, to, FriendStatus.APPLY);
    }

    public boolean isSupport(FriendStatus status) {
        return this.friendStatus == status;
    }

    public void approve() {
        this.friendStatus = FriendStatus.ACCEPT;
    }
}
