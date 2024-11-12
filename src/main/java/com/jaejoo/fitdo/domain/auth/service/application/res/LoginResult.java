package com.jaejoo.fitdo.domain.auth.service.application.res;

import com.jaejoo.fitdo.domain.auth.service.domain.dto.TokenSet;
import com.jaejoo.fitdo.domain.user.core.Account;
import lombok.Getter;

@Getter
public class LoginResult {
    private final String accessToken;
    private final String refreshToken;
    private final Boolean isNewFlag;

    public LoginResult(String accessToken, String refreshToken, Boolean isNewFlag) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isNewFlag = isNewFlag;
    }

    public static LoginResult from(TokenSet tokenSet, Account account){
        return new LoginResult(tokenSet.accessToken(), tokenSet.refreshToken(), account.isNewUser());
    }
}
