package com.jaejoo.fitdo.domain.user.core;

import lombok.Getter;

public class Account {
    @Getter
    private final String authId;
    private final Boolean newFlag;

    public Account(String authId, Boolean isNewFlag) {
        this.authId = authId;
        this.newFlag = isNewFlag;
    }

    public Boolean isNewUser() {
        return newFlag;
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