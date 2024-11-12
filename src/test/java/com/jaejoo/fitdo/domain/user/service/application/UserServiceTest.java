package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

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
            boolean isNewFlag = false;

            GrantRole roleUser = GrantRole.ROLE_USER;
            User mockuser = new User(new Account(authId, isNewFlag, id, roleUser, AuthType.KAKAO));
            User registerUser = mockuser.register(height, weight);

            when(userRepository.findById(id)).thenReturn(mockuser);
            when(userRepository.save(any())).thenReturn(registerUser);

            // when
            System.out.println("=====Logic Start=====");

            User user = userService.completeSignUp(new CompleteSignUpCommand(id, height, weight));

            System.out.println("=====Logic End=====");
            // then
            assertThat(user).isEqualTo(registerUser);
        }
    }
}