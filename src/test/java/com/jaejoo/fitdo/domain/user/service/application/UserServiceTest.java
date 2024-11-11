package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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

            when(userRepository.findById(id)).thenReturn(new User(new Account(authId, isNewFlag, id), height, weight));

            // when
            System.out.println("=====Logic Start=====");

            User user = userService.completeSignUp(id, height, weight);

            System.out.println("=====Logic End=====");
            // then
            assertThat(user).isEqualTo(new User(new Account(authId, true, id), height, weight));
        }
    }
}