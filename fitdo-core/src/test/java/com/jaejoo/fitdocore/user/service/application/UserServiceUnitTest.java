package com.jaejoo.fitdocore.user.service.application;


import com.jaejoo.fitdocore.user.UserService;
import com.jaejoo.fitdocore.user.req.CompleteSignUpCommand;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("사용자의 회원가입완료기능")
    class completeRegister {
        @Test
        void 키와몸무게추가하면_회원가입완료() {
            // given
            int height = 180;
            int weight = 70;
            Long id = 1L;
            String authId = "authId";
            String nickname = "nickname";
            boolean isNewFlag = false;

            GrantRole roleUser = GrantRole.ROLE_USER;
            User mockuser = new User(new Account(authId, isNewFlag, id, roleUser, AuthType.KAKAO, true));
            User registerUser = mockuser.register(height, weight, nickname);

            when(userRepository.findById(id)).thenReturn(mockuser);
            when(userRepository.save(any())).thenReturn(registerUser);

            // when
            System.out.println("=====Logic Start=====");

            boolean isComplete = userService.completeSignUp(new CompleteSignUpCommand(id, height, weight, nickname));

            System.out.println("=====Logic End=====");
            // then
            assertThat(isComplete).isTrue();
        }
    }
}