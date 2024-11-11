package com.jaejoo.fitdo.domain.user.infra.repository.impl;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.infra.repository.AccountRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public Account findOrSaveByAuthId(String authId, AuthType platformType, String username) {
        return userJpaRepository.findByAuthId(authId)
                .orElseGet(() -> userJpaRepository.save(UserJpaEntity.from(authId, platformType, username, false)))
                .toAccountModel();
    }
}
