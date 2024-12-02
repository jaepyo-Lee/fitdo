package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.infra.repository.CategoryCommandRepository;
import com.jaejoo.fitdo.domain.exercise.service.domain.CategoryInitializer;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import com.jaejoo.fitdo.domain.user.service.application.req.NickNameIsDuplicateCommand;
import com.jaejoo.fitdo.domain.user.service.application.res.IsNickNameDuplicateResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryInitializer categoryInitializer;
    @Mock
    private CategoryCommandRepository categoryCommandRepository;

    @Nested
    @DisplayName("사용자의 회원가입완료기능")
    class completeRegister {
        @Test
        void success() {
            // given
            int height = 180;
            int weight = 70;
            Long id = 1L;
            String authId = "authId";
            String nickname = "nickname";
            boolean isNewFlag = false;

            GrantRole roleUser = GrantRole.ROLE_USER;
            User mockuser = new User(new Account(authId, isNewFlag, id, roleUser, AuthType.KAKAO));
            User registerUser = mockuser.register(height, weight, nickname);

            when(userRepository.findById(id)).thenReturn(mockuser);
            when(userRepository.save(any())).thenReturn(registerUser);
            when(categoryInitializer.init(any())).thenReturn(List.of());
            doNothing().when(categoryCommandRepository).saveAll(anyList());


            // when
            System.out.println("=====Logic Start=====");

            User user = userService.completeSignUp(new CompleteSignUpCommand(id, height, weight, nickname));

            System.out.println("=====Logic End=====");
            // then
            assertThat(user).isEqualTo(registerUser);
        }
    }
}