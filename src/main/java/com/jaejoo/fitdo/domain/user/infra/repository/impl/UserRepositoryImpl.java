package com.jaejoo.fitdo.domain.user.infra.repository.impl;

import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .toUserModel();
    }

    @Transactional
    @Override
    public User save(User user) {
        UserJpaEntity saveUserJpaEntity = UserJpaEntity.from(user);
        return userJpaRepository.save(saveUserJpaEntity).toUserModel();
    }
}
