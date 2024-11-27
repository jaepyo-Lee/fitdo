package com.jaejoo.fitdo.domain.user.infra.repository;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;

public interface FriendRepository {
    FriendJpaEntity save(FriendJpaEntity friend);
}
