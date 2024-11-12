package com.jaejoo.fitdo.domain.user.web.req;

import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private Integer height;
    private Integer weight;

    public CompleteSignUpCommand toCommand(Long userId){
        return new CompleteSignUpCommand(userId, height, weight);
    }
}
