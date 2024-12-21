package com.jaejoo.fitdomysql.domain.user.repository.impl;


import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.AccountRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Account findOrSaveByAuthId(String authId, AuthType platformType, String username) {
        return userJpaRepository.findByAuthId(authId)
                .orElseGet(() -> userJpaRepository.save(UserJpaEntity.from(authId, platformType, username, false, GrantRole.ROLE_USER)))
                .toAccountModel();
    }

    @Override
    public Account findByUserId(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"))
                .toAccountModel();
    }
}
