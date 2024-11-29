package com.jaejoo.fitdo.domain.user.infra.repository;

import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;

import java.util.List;

public interface FriendRepository {
    FriendJpaEntity save(FriendJpaEntity friend);

    FriendJpaEntity findBySenderAndReceiver(Long senderId, Long receiverId);


    List<FriendJpaEntity> findAllByReceiverId(Long receiverId);
}
