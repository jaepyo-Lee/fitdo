package com.jaejoo.fitdomysql.domain.user.core;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Account {
    private final String authId;
    private final Boolean newFlag;
    private final Long userId;
    private final GrantRole role;
    private final AuthType authType;
    private final Boolean active;

    public Account(String authId, Boolean isNewFlag, Long userId, GrantRole role, AuthType authType, Boolean active) {
        this.authId = authId;
        this.newFlag = isNewFlag;
        this.userId = userId;
        this.role = role;
        this.authType = authType;
        this.active = active;
    }

    public Boolean isNewUser() {
        return newFlag;
    }

    public Account complete() {
        return new Account(authId, true, userId, role, authType, active);
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