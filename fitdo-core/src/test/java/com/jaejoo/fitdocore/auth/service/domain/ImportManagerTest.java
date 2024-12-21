package com.jaejoo.fitdocore.auth.service.domain;

import com.jaejoo.fitdocore.auth.client.impl.KakaoImporter;
import com.jaejoo.fitdocore.auth.client.res.OAuthUserDate;
import com.jaejoo.fitdocore.auth.domain.ImportManager;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType.NOT_APPLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImportManagerTest {
    @Mock
    private KakaoImporter kakaoImporter;

    private ImportManager importManager;

    @BeforeEach
    void setup() {
        importManager = new ImportManager(List.of(kakaoImporter));
    }

    @Nested
    @DisplayName("OAuth 사용자 정보조회")
    class OAuthTest {
        @Test
        void success_kakao_test() {
            // given

            AuthType kakao = AuthType.KAKAO;
            String authorizationToken = "authorizationToken";
            String authId = "authId";
            String name = "name";
            when(kakaoImporter.isSupport(kakao)).thenReturn(true);

            when(kakaoImporter.getData(authorizationToken)).thenReturn(new OAuthUserDate(authId, name));

            // when
            System.out.println("=====Logic Start=====");

            OAuthUserDate oAuthUserDate = importManager.importData(authorizationToken, kakao);

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(oAuthUserDate.getAuthId()).isEqualTo(authId),
                    () -> assertThat(oAuthUserDate.getUsername()).isEqualTo(name));
        }

        @Test
        void fail_not_apply_platform() {
            // given
            AuthType kakao = NOT_APPLY;
            String authorizationToken = "authorizationToken";
            String authId = "authId";
            String name = "name";

            // when
            System.out.println("=====Logic Start=====");
            System.out.println("=====Logic End=====");
            // then
            assertThrows(IllegalArgumentException.class, () -> importManager.importData(authorizationToken, NOT_APPLY));
        }
    }
}