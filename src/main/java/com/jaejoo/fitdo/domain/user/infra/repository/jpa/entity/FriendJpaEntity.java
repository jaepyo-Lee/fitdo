package com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class FriendJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "my_id")
    private UserJpaEntity from;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private UserJpaEntity to;

    @Enumerated(EnumType.STRING)
    private FriendStatus friendStatus;

    public FriendJpaEntity(UserJpaEntity from, UserJpaEntity to, FriendStatus friendStatus) {
        this.from = from;
        this.to = to;
        this.friendStatus = friendStatus;
    }

    public static FriendJpaEntity apply(UserJpaEntity from, UserJpaEntity to) {
        return new FriendJpaEntity(from, to, FriendStatus.WAIT);
    }

    public boolean isSupport(FriendStatus status) {
        return this.friendStatus == status;
    }
}
