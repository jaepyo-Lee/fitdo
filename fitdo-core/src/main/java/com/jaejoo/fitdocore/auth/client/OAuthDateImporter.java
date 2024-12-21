package com.jaejoo.fitdocore.auth.client;


import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;

public interface OAuthDateImporter {
    OAuthUserDate getData(String token);

    Boolean isSupport(AuthType authType);
}
