package com.jaejoo.fitdo.domain.core;

import com.jaejoo.fitdo.domain.user.core.Account;
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
            Account account = new Account("#12313", true);
            // when
            // then
            assertThat(account.isNewUser()).isTrue();
        }

        @Test
        void isFail() {
            // given
            Account account = new Account("#12313", false);
            // when
            // then
            assertThat(account.isNewUser()).isFalse();
        }
    }
}