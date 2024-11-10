package com.jaejoo.fitdo.domain.auth.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.LoginCreateCommand;
import com.jaejoo.fitdo.domain.auth.service.application.res.LoginResult;
import com.jaejoo.fitdo.domain.auth.service.domain.ImportManager;
import com.jaejoo.fitdo.domain.auth.service.domain.JwtProvider;
import com.jaejoo.fitdo.domain.auth.service.domain.dto.TokenSet;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.infra.repository.AccountRepository;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final ImportManager importManager;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(ImportManager importManager, AccountRepository accountRepository, JwtProvider jwtProvider) {
        this.importManager = importManager;
        this.accountRepository = accountRepository;
        this.jwtProvider = jwtProvider;
    }

    public LoginResult login(LoginCreateCommand request) {
        OAuthUserDate oAuthUserDate = importManager.importData(request.getAuthorizationToken(), request.getPlatformType());
        Account account = accountRepository.findOrSaveByAuthId(oAuthUserDate.getAuthId(), request.getPlatformType(), oAuthUserDate.getUsername());
        TokenSet tokenSet = jwtProvider.createTokenSet(account, request.getPlatformType());
        return LoginResult.from(tokenSet, account);
    }
}
