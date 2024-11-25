package com.jaejoo.fitdo.domain.user.service.application;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.core.User;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import com.jaejoo.fitdo.domain.user.service.application.req.CompleteSignUpCommand;
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
    private CategoryJpaRepository categoryJpaRepository;
    @Test
    void 사용자_가입완료_기능() {
        // given
        UserJpaEntity saveUser = userJpaRepository.save(UserJpaEntity.builder().build());

        // when
        System.out.println("=====Logic Start=====");

        CompleteSignUpCommand command = new CompleteSignUpCommand(saveUser.getId(), 180, 80);
        User user = userService.completeSignUp(command);

        System.out.println("=====Logic End=====");
        // then
        List<CategoryJpaEntity> allByUserId = categoryJpaRepository.findAllByUserId(saveUser.getId());
        assertAll(()-> assertThat(user.getHeight()).isEqualTo(command.getHeight()),
                ()-> assertThat(user.getWeight()).isEqualTo(command.getWeight()),
                ()-> assertThat(allByUserId.size()).isEqualTo(BodyPart.values().length));
    }
}