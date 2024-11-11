package com.jaejoo.fitdo.domain.user.core;

import lombok.Getter;

import java.util.Objects;

public class Account {
    @Getter
    private final String authId;
    private final Boolean newFlag;
    private final Long userId;

    public Account(String authId, Boolean isNewFlag, Long userId) {
        this.authId = authId;
        this.newFlag = isNewFlag;
        this.userId = userId;
    }

    public Boolean isNewUser() {
        return newFlag;
    }

    public Account complete() {
        return new Account(authId, true, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(authId, account.authId) && Objects.equals(newFlag, account.newFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authId, newFlag);
    }
}

/**
 * 로그인
 * <p>
 * User -> 첫 회원인가?
 * User -> return false
 * <p>
 * User -> 기존 회원
 * return JwtProvider.create(user.유일값);
 */