package com.jaejoo.fitdocore.auth.service.application;

import com.jaejoo.fitdocore.auth.AuthService;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    @DisplayName("회원 탈퇴시 soft-delete 테스트")
    @Test
    void outServiceTest() {
        // given
        UserJpaEntity userJpaEntity = UserJpaEntity.builder()
                .authId("12345")
                .authType(AuthType.KAKAO)
                .height(172)
                .weight(72)
                .newFlag(false)
                .username("이재표")
                .role(GrantRole.ROLE_USER)
                .nickname("Lee")
                .build();
        User saveUser = userRepository.save(userJpaEntity.toUserModel());

        // when
        authService.out(saveUser.getUserId());

        // then
        User user = userRepository.findById(saveUser.getUserId());
        Assertions.assertThat(user.getAccount().getActive()).isEqualTo(false);
    }
}
