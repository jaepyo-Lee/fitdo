package com.jaejoo.fitdocore.auth.client.res;

import lombok.Getter;

@Getter
public class OAuthUserDate {
    private final String authId;
    private final String username;

    public OAuthUserDate(String authId, String username) {
        this.authId = authId;
        this.username = username;
    }
}
