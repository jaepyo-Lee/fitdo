package com.jaejoo.fitdo.domain.common;

public class HeaderUtil {
    private final static String TOKEN_PREFIX = "Bearer ";

    public static String parseBearer(String bearerToken) {
        if (bearerToken == null) {
            throw new IllegalArgumentException("Bearer토큰이 포함되어야합니다.");
        }

        String bearer = bearerToken.substring(0, TOKEN_PREFIX.length());
        if(!bearer.equals(TOKEN_PREFIX)){
            throw new IllegalArgumentException("Bearer타입외의 토큰입니다.");
        }
        String token = bearerToken.substring(TOKEN_PREFIX.length());
        if (token.isEmpty()) {
            throw new IllegalArgumentException("토큰이 빈 문자열입니다.");
        }
        return bearerToken.substring(TOKEN_PREFIX.length());
    }
}
