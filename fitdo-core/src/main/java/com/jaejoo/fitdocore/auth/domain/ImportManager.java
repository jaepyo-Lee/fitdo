package com.jaejoo.fitdocore.auth.domain;

import com.jaejoo.fitdocore.auth.client.OAuthDateImporter;
import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
