package com.jaejoo.fitdocore.auth.service.application;

import com.jaejoo.fitdocore.auth.AuthService;
import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdocore.auth.domain.ImportManager;
import com.jaejoo.fitdocore.auth.jwt.JwtProvider;
import com.jaejoo.fitdocore.auth.jwt.dto.TokenSet;
import com.jaejoo.fitdocore.auth.req.LoginCreateCommand;
import com.jaejoo.fitdocore.auth.res.LoginResult;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.AccountRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {
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
            Account account = new Account(authId, isNewFlag, userId, GrantRole.ROLE_USER, AuthType.KAKAO, true);

            when(importManager.importData(authorizationToken, kakao))
                    .thenReturn(new OAuthUserDate(authId, name));
            when(accountRepository.findOrSaveByAuthId(authId, kakao, name)).thenReturn(account);
            when(jwtProvider.createTokenSet(account, kakao)).thenReturn(new TokenSet(accessToken, refreshToken));

            // when
            System.out.println("=====Logic Start=====");

            LoginResult login = authService.login(new LoginCreateCommand(authorizationToken, kakao));

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(login.getAccessToken()).isEqualTo(accessToken),
                    () -> assertThat(login.getIsNewFlag()).isEqualTo(isNewFlag),
                    () -> assertThat(login.getRefreshToken()).isEqualTo(refreshToken));
        }
    }
}