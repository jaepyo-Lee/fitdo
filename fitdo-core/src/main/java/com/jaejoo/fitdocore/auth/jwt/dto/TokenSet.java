package com.jaejoo.fitdocore.auth.jwt.dto;


public record TokenSet(String accessToken, String refreshToken) {

    public static TokenSet ofBearer(String accessToken, String refreshToken) {
        return new TokenSet(accessToken, refreshToken);
    }
}
