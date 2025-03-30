package com.jaejoo.fitdocore.auth.service.domain;

import com.jaejoo.fitdocore.auth.jwt.JwtProvider;
import com.jaejoo.fitdocore.auth.jwt.dto.TokenSet;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {
    @InjectMocks
    private JwtProvider jwtProvider; // The class under test

    private long TEST_ACCESSTOKEN_MILLISECOND = 604800000L;
    private long TEST_REFRESHTOKEN_MILLITIME = TEST_ACCESSTOKEN_MILLISECOND * 4;
    private Key key;
    private String secret = "testSecrettestSecrettestSecrettestSecrettestSecrettestSecret";

    private String account = "123";
    private AuthType loginType = AuthType.KAKAO;

    private Account mockAccount = new Account(account, true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO, true); // Mock user


    @BeforeEach
    public void setUp() {
        // Manually initializing the key as it is done in the init() method of JwtProvider
        key = Keys.hmacShaKeyFor(secret.getBytes());
        jwtProvider = new JwtProvider(key, TEST_REFRESHTOKEN_MILLITIME, TEST_ACCESSTOKEN_MILLISECOND);
    }

    @Nested
    @DisplayName("토큰생성테스트")
    class createJwtTokenSetTest {
        @Test
        void success() {
            // given
            TokenSet tokenSet = jwtProvider.createTokenSet(mockAccount, loginType);

            // when
            System.out.println("=====Logic Start=====");

            System.out.println("=====Logic End=====");
            // then
            assertNotNull(tokenSet);
            assertNotNull(tokenSet.accessToken());
            assertNotNull(tokenSet.refreshToken());
        }
    }



    /*@Nested
    @DisplayName("Authentication 조회테스트")
    class getAuthenticationTest {
        @Test
        void success() {
            // given
            String token = jwtProvider.createTokenSet(mockUser, loginType).accessToken();
            when(authTokenMock.validate()).thenReturn(true);
            when(authTokenMock.getTokenClaims()).thenReturn(Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody());

            // when
            System.out.println("=====Logic Start=====");

            Authentication authentication = jwtProvider.getAuthentication(authTokenMock);


            System.out.println("=====Logic End=====");
            // then
            assertNotNull(authentication);
            assertEquals(senderId, ((CustomUserDetail) authentication.getPrincipal()).senderId());
            assertEquals(username, ((CustomUserDetail) authentication.getPrincipal()).getUsername());
        }
        @Test
        public void testGetAuthentication() {
            // Arrange
            String token = jwtProvider.createTokenSet(mockUser, loginType).accessToken();
            when(authTokenMock.validate()).thenReturn(true);
            when(authTokenMock.getTokenClaims()).thenReturn(Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody());

            // Act
            Authentication authentication = jwtProvider.getAuthentication(authTokenMock);

            // Assert
            assertNotNull(authentication);
            assertEquals(senderId, ((CustomUserDetail) authentication.getPrincipal()).senderId());
            assertEquals(username, ((CustomUserDetail) authentication.getPrincipal()).getUsername());
        }

        @Test
        public void testGetAuthentication_ThrowsException() {
            // Arrange
            String token = "invalidToken";
            when(authTokenMock.validate()).thenReturn(false);

            // Act & Assert
            assertThrows(TokenValidFailedException.class, () -> {
                jwtProvider.getAuthentication(authTokenMock);
            });
        }
    }*/
}