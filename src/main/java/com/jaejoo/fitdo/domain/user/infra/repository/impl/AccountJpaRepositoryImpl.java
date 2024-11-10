package com.jaejoo.fitdo.domain.user.infra.repository.impl;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.infra.repository.AccountRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountJpaRepositoryImpl implements AccountRepository {
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public AccountJpaRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Account findOrSaveByAuthId(String authId, AuthType platformType, String username) {
        return userJpaRepository.findByAuthId(authId)
                .orElseGet(() -> userJpaRepository.save(UserJpaEntity.from(authId, platformType, username, false)))
                .toAccountModel();
    }
}
