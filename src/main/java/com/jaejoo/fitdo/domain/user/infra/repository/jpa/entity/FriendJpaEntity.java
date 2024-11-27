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

    @Builder
    public FriendJpaEntity(UserJpaEntity from, UserJpaEntity to) {
        this.from = from;
        this.to = to;
    }
}
