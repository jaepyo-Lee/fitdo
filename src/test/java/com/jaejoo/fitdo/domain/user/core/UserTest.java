package com.jaejoo.fitdo.domain.user.core;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Nested
    @DisplayName("사용자등록테스트")
    class registerTest {
        @Test
        void success() {
            // given
            String authId = "authId";
            int weight = 75;
            int height = 180;
            boolean isNewFlag = false;
            String nickname = "nickname";
            User user = new User(new Account(authId, isNewFlag, 1L, GrantRole.ROLE_USER, AuthType.KAKAO));

            // when
            System.out.println("=====Logic Start=====");

            User actual = user.register(height, weight, nickname);

            System.out.println("=====Logic End=====");

            // then
            assertEquals(new User(new Account(authId, true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO), height, weight, nickname), actual);
        }
    }
}