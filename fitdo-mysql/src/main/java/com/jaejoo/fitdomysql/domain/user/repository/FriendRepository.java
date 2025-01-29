package com.jaejoo.fitdomysql.domain.user.repository;

import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;

import java.util.List;

public interface FriendRepository {
    FriendJpaEntity save(FriendJpaEntity friend);

    List<FriendJpaEntity> findAllBySenderId(Long senderId);
}
