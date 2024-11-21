package com.jaejoo.fitdo.domain.auth.core;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    @Nested
    @DisplayName("신규 사용자여부 확인테스트")
    class testIsNewSignTest {
        @Test
        void isTrue() {
            // given
            Account account = new Account("#12313", true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO);
            // when
            // then
            assertThat(account.isNewUser()).isTrue();
        }

        @Test
        void isFail() {
            // given
            Account account = new Account("#12313", false,  1L, GrantRole.ROLE_USER,  AuthType.KAKAO);
            // when
            // then
            assertThat(account.isNewUser()).isFalse();
        }
    }

    @Nested
    @DisplayName("사용자의 회원가입 완료기능 테스트")
    class completeSignupTest {
        @Test
        void success() {
            // given
            String authId = "#12313";
            Account account = new Account(authId, false,  1L, GrantRole.ROLE_USER, AuthType.KAKAO );

            // when
            System.out.println("=====Logic Start=====");

            Account actual = account.complete();

            System.out.println("=====Logic End=====");
            // then
            assertThat(actual).isEqualTo(new Account(authId, true,  1L, GrantRole.ROLE_USER,  AuthType.KAKAO));
        }
    }
}