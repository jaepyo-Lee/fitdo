package com.jaejoo.fitdocore.auth.req;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import lombok.Getter;

@Getter
public class LoginCreateCommand {
    private final String authorizationToken;
    private final AuthType platformType;

    public LoginCreateCommand(String token, AuthType platform) {
        this.platformType = platform;
        this.authorizationToken = token;
    }

    public static LoginCreateCommand of(String token, String platform) {
        return new LoginCreateCommand(token, AuthType.valueOf(platform));
    }
}
