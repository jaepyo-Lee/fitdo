package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class FriendJpaEntity {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private UserJpaEntity sender;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private UserJpaEntity receiver;

    public FriendJpaEntity(UserJpaEntity sender, UserJpaEntity receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public static FriendJpaEntity apply(UserJpaEntity from, UserJpaEntity to) {
        return new FriendJpaEntity(from, to);
    }
}
