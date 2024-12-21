package com.jaejoo.fitdocore.auth;


import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdocore.auth.domain.ImportManager;
import com.jaejoo.fitdocore.auth.jwt.JwtProvider;
import com.jaejoo.fitdocore.auth.jwt.dto.TokenSet;
import com.jaejoo.fitdocore.auth.req.LoginCreateCommand;
import com.jaejoo.fitdocore.auth.res.LoginResult;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final ImportManager importManager;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;

    public LoginResult login(LoginCreateCommand request) {

        OAuthUserDate oAuthUserDate = importManager.importData(request.getAuthorizationToken(), request.getPlatformType());
        Account account = accountRepository.findOrSaveByAuthId(oAuthUserDate.getAuthId(), request.getPlatformType(), oAuthUserDate.getUsername());
        TokenSet tokenSet = jwtProvider.createTokenSet(account, request.getPlatformType());
        return LoginResult.from(tokenSet, account);
    }
}
