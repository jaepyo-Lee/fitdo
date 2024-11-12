package com.jaejoo.fitdo.domain.user.web.res;

import lombok.Getter;

@Getter
public class UserRegisterResponse {
    private final Boolean isCompleteRegister;

    public UserRegisterResponse(Boolean isCompleteRegister) {
        this.isCompleteRegister = isCompleteRegister;
    }
}
