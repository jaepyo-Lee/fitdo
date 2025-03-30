package com.jaejoo.fitdocore.user.service.application;


import com.jaejoo.fitdocore.user.UserService;
import com.jaejoo.fitdocore.user.req.CompleteSignUpCommand;
import com.jaejoo.fitdocore.user.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdocore.user.res.IsNickNameDuplicateResult;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserServiceIntegrateTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자_가입완료_기능() {
        // given

        UserJpaEntity saveUser = userJpaRepository.save(UserJpaEntity.builder().build());

        // when
        System.out.println("=====Logic Start=====");

        CompleteSignUpCommand command = new CompleteSignUpCommand(saveUser.getId(), 180, 80, "nickname");
        boolean isComplete = userService.completeSignUp(command);

        System.out.println("=====Logic End=====");
        // then
        assertThat(isComplete).isTrue();

    }

    @Nested
    @DisplayName("닉네임중복여부확인")
    class duplicateNicknameTest {
        @Test
        void 중복된닉네임이들어왔을때() {
            // given
            String nickname = "nickname";
            User save = userRepository.save(new User(new Account("authId", true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO, true), 180, 80, nickname));
            System.out.println(save.getNickname());
            // when
            System.out.println("=====Logic Start=====");

            IsNickNameDuplicateResult result = userService.isDuplicate(new NickNameIsDuplicateCommand(nickname));

            System.out.println("=====Logic End=====");
            // then
            assertThat(result.isDuplicateNickname()).isTrue();
        }

        @Test
        void 닉네임이중복되지않았을때() {
            // given
            String nickname = "nickname";
            User save = userRepository.save(new User(new Account("authId", true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO, true), 180, 80, nickname));

            // when
            System.out.println("=====Logic Start=====");

            IsNickNameDuplicateResult result = userService.isDuplicate(new NickNameIsDuplicateCommand("nickname1"));

            System.out.println("=====Logic End=====");
            // then
            assertThat(result.isDuplicateNickname()).isFalse();
        }
    }
}