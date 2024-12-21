package com.jaejoo.fitdocore.auth.client.impl;


import com.jaejoo.fitdocore.auth.client.OAuthDateImporter;
import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoImporter implements OAuthDateImporter {
    private final static String AUTH_SERVER_URI = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient;

    public KakaoImporter(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public KakaoImporter() {
        this.webClient = WebClient.create();
    }

    @Override
    public OAuthUserDate getData(String token) {
        return webClient.post()
                .uri(AUTH_SERVER_URI)
                .headers(httpHeaders -> httpHeaders.set("Authorization", "Bearer " + token))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoUserResponseDto.class)
                .block().toCommonDto();
    }

    @Override
    public Boolean isSupport(AuthType authType) {
        if (authType.equals(AuthType.KAKAO)) {
            return true;
        }
        return false;
    }
}
