package com.jaejoo.fitdo.domain.exercise.service.domain;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryInitializerTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private CategoryInitializer categoryInitializer;

    @Test
    void 사용자_운동부위_초기화() {
        // given
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        UserJpaEntity saveUser = userJpaRepository.save(userJpaEntity);

        // when
        System.out.println("=====Logic Start=====");
        List<CategoryJpaEntity> categories = categoryInitializer.init(saveUser);

        System.out.println("=====Logic End=====");
        // then
        assertThat(categories.size()).isEqualTo(8);

    }

}