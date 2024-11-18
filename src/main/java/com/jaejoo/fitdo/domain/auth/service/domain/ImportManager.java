package com.jaejoo.fitdo.domain.auth.service.domain;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.global.client.OAuthDateImporter;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.jaejoo.fitdo.domain.auth.service.application.req.AuthType.KAKAO;

@Component
@RequiredArgsConstructor
public class ImportManager {
    private final List<OAuthDateImporter> importers;

    public OAuthUserDate importData(String authorizationToken, AuthType platform) {
        for (OAuthDateImporter importer : importers) {
            if (!importer.isSupport(platform)) {
                continue;
            }
            return importer.getData(authorizationToken);
        }
        throw new IllegalArgumentException("지원하지 않는 플랫폼입니다.");
    }
}
