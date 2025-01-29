package com.jaejoo.fitdomysql.domain.user.repository.impl;

import com.jaejoo.fitdomysql.domain.user.repository.FriendRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
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
    public List<FriendJpaEntity> findAllBySenderId(Long senderId) {
        return friendJpaRepository.findAllBySenderId(senderId);
    }
}
