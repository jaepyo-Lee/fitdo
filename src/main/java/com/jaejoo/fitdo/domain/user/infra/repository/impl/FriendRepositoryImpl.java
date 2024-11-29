package com.jaejoo.fitdo.domain.user.infra.repository.impl;

import com.jaejoo.fitdo.domain.user.infra.repository.FriendRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.FriendJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRepositoryImpl implements FriendRepository {
    private final FriendJpaRepository friendJpaRepository;

    @Override
    public FriendJpaEntity save(FriendJpaEntity friend) {
        return friendJpaRepository.save(friend);
    }

    @Override
    public FriendJpaEntity findBySenderAndReceiver(Long senderId, Long receiverId) {
        return friendJpaRepository.findBySenderAndReceiver(senderId, receiverId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }

    @Override
    public List<FriendJpaEntity> findAllByReceiverId(Long receiverId) {
        return friendJpaRepository.findAllByToId(receiverId);
    }
}
