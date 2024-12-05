package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdo.domain.user.service.application.res.IsNickNameDuplicateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        User user = userService.completeSignUp(command);

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(user.getHeight()).isEqualTo(command.getHeight()),
                () -> assertThat(user.getWeight()).isEqualTo(command.getWeight()));
    }

    @Nested
    @DisplayName("닉네임중복여부확인")
    class duplicateNicknameTest {
        @Test
        void 중복된닉네임이들어왔을때() {
            // given
            String nickname = "nickname";
            User save = userRepository.save(new User(new Account("authId", true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO), 180, 80, nickname));
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
            User save = userRepository.save(new User(new Account("authId", true, 1L, GrantRole.ROLE_USER, AuthType.KAKAO), 180, 80, nickname));

            // when
            System.out.println("=====Logic Start=====");

            IsNickNameDuplicateResult result = userService.isDuplicate(new NickNameIsDuplicateCommand("nickname1"));

            System.out.println("=====Logic End=====");
            // then
            assertThat(result.isDuplicateNickname()).isFalse();
        }
    }
}