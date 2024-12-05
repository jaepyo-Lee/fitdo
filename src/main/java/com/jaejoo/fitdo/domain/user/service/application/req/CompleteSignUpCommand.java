package com.jaejoo.fitdo.domain.user.service.application.req;

import lombok.Getter;

@Getter
public class CompleteSignUpCommand {
    private final Long userId;
    private final Integer height;
    private final Integer weight;
    private final String nickname;
    public CompleteSignUpCommand(Long userId, Integer height, Integer weight,String nickname) {
        this.userId = userId;
        this.height = height;
        this.weight = weight;
        this.nickname = nickname;
    }
}
