package com.jaejoo.fitdo.domain.auth.service.application.req;

import lombok.Getter;

@Getter
public class LoginCreateCommand {
    private final String authorizationToken;
    private final AuthType platformType;

    public LoginCreateCommand(String token, AuthType platform) {
        this.platformType = platform;
        this.authorizationToken = token;
    }

    public static LoginCreateCommand of(String token, AuthType platform){
        return new LoginCreateCommand(token,platform);
    }
}
