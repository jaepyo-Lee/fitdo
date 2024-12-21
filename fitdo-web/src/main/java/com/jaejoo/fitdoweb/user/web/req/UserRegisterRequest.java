package com.jaejoo.fitdoweb.user.web.req;

import com.jaejoo.fitdocore.user.req.CompleteSignUpCommand;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private Integer height;
    private Integer weight;
    private String nickname;

    public CompleteSignUpCommand toCommand(Long userId) {
        return new CompleteSignUpCommand(userId, height, weight,nickname);
    }
}
