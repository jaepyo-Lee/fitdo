package com.jaejoo.fitdo.global.client;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;

public interface OAuthDateImporter {
    OAuthUserDate getData(String token);

    Boolean isSupport(AuthType authType);
}
