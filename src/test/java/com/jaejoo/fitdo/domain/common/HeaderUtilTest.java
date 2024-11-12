package com.jaejoo.fitdo.domain.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HeaderUtilTest {
    @Nested
    @DisplayName("Bearer토큰 헤더 파싱 util테스트")
    class HeaderUtilParseTest {
        @Test
        void success() {
            // given
            final String TOKEN = "token";
            final String CORRECT_BEARER_TOKEN = "Bearer " + TOKEN;
            // when
            System.out.println("=====Logic Start=====");
            String token = HeaderUtil.parseBearer(CORRECT_BEARER_TOKEN);
            System.out.println("=====Logic End=====");
            // then
            assertThat(token).isEqualTo(TOKEN);
        }

        @Test
        void Bearer토큰이아닐경우() {
            // given
            final String ANOTHER_TYPE_TOKEN = "Bearers token";

            // when
            System.out.println("=====Logic Start=====");
            System.out.println("=====Logic End=====");
            // then
            assertThrows(IllegalArgumentException.class, () -> HeaderUtil.parseBearer(ANOTHER_TYPE_TOKEN));
        }

        @Test
        void Bearer길이가맞지않을경우() {
            // given
            final String NOT_MATCH_LENGTH_TOKEN = "Bearers token";
            // when
            System.out.println("=====Logic Start=====");
            System.out.println("=====Logic End=====");
            // then
            assertThrows(IllegalArgumentException.class, () -> HeaderUtil.parseBearer(NOT_MATCH_LENGTH_TOKEN));
        }

        @Test
        void 토큰이없을경우() {
            // given
            final String EMPTY_TOKEN = "";
            // when
            System.out.println("=====Logic Start=====");
            System.out.println("=====Logic End=====");
            // then
            assertThrows(IllegalArgumentException.class, () -> HeaderUtil.parseBearer(EMPTY_TOKEN));
        }
    }
}