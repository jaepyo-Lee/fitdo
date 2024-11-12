package com.jaejoo.fitdo.domain.auth.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.auth.service.application.req.LoginCreateCommand;
import com.jaejoo.fitdo.domain.auth.service.application.res.LoginResult;
import com.jaejoo.fitdo.domain.auth.service.domain.ImportManager;
import com.jaejoo.fitdo.domain.auth.service.domain.JwtProvider;
import com.jaejoo.fitdo.domain.auth.service.domain.dto.TokenSet;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.AccountRepository;
import com.jaejoo.fitdo.global.client.res.OAuthUserDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private ImportManager importManager;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private AuthService authService;

    @Nested
    @DisplayName("로그인 단위 테스트")
    class loginTest {
        @Test
        void success() {
            // given
            String authId = "authId";
            String name = "name";
            String authorizationToken = "authorizationToken";
            AuthType kakao = AuthType.KAKAO;
            long userId = 1L;
            boolean isNewFlag = true;
            String refreshToken = "refreshToken";
            String accessToken = "accessToken";
            Account account = new Account(authId, isNewFlag, userId, GrantRole.ROLE_USER, AuthType.KAKAO);

            when(importManager.importData(authorizationToken, kakao))
                    .thenReturn(new OAuthUserDate(authId, name));
            when(accountRepository.findOrSaveByAuthId(authId, kakao, name)).thenReturn(account);
            when(jwtProvider.createTokenSet(account, kakao)).thenReturn(new TokenSet(accessToken, refreshToken));

            // when
            System.out.println("=====Logic Start=====");

            LoginResult login = authService.login(LoginCreateCommand.of(authorizationToken, kakao));

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(login.getAccessToken()).isEqualTo(accessToken),
                    () -> assertThat(login.getIsNewFlag()).isEqualTo(isNewFlag),
                    () -> assertThat(login.getRefreshToken()).isEqualTo(refreshToken));
        }
    }
}