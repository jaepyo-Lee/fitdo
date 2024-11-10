package com.jaejoo.fitdo.domain.auth.service.domain;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.global.client.OAuthDateImporter;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.jaejoo.fitdo.domain.auth.service.application.req.AuthType.KAKAO;

@Component
@RequiredArgsConstructor
public class ImportManager {
    private final OAuthDateImporter kakaoImporter;

    public OAuthUserDate importData(String authorizationToken, AuthType platform) {
        OAuthUserDate oAuthUserDate = null;
        if (isKAKAO(platform)) {
            oAuthUserDate = kakaoImporter.getData(authorizationToken);
        } else {
            throw new IllegalArgumentException("지원하지 않는 플랫폼입니다.");
        }
        return oAuthUserDate;
    }

    private static boolean isKAKAO(AuthType platform) {
        return platform.equals(KAKAO);
    }
}
