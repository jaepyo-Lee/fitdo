package com.jaejoo.fitdo.global.client.impl;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class KakaoUserResponseDto {
    private Long id;
    private Properties properties;

    @ToString
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Properties {
        private String nickname;
    }

    public OAuthUserDate toCommonDto() {
        return new OAuthUserDate(String.valueOf(this.id), this.properties.nickname);
    }
}
