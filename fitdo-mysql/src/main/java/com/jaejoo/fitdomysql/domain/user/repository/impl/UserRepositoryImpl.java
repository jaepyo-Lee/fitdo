package com.jaejoo.fitdomysql.domain.user.repository.impl;

import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
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

    @Override
    public boolean isExistNickName(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    public Integer findAllSize() {
        return userJpaRepository.findAll().size();
    }
}
