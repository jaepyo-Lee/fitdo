package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.CategoryQueryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import(CategoryQueryJpaRepository.class)
@DataJpaTest
class CategoryQueryRepositoryTest {

    @Autowired
    private CategoryQueryRepository categoryQueryRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    /*@BeforeEach
    void init() {
        categoryJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }*/

    @Test
    void 사용자의운동카테고리조회() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity user2 = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);
        UserJpaEntity saveUser2 = userJpaRepository.save(user2);

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build();
        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().categoryName("등1").user(saveUser).build();

        CategoryJpaEntity chestCategory2 = CategoryJpaEntity.builder().categoryName("가슴").user(saveUser2).build();
        CategoryJpaEntity backCategory2 = CategoryJpaEntity.builder().categoryName("등").user(saveUser2).build();

        CategoryJpaEntity save = categoryJpaRepository.save(chestCategory);
        CategoryJpaEntity save1 = categoryJpaRepository.save(backCategory);

        CategoryJpaEntity save2 = categoryJpaRepository.save(chestCategory2);
        CategoryJpaEntity save3 = categoryJpaRepository.save(backCategory2);

        // when
        System.out.println("=====Logic Start=====");

        CategoryJpaEntity categoryByUser = categoryQueryRepository.findById(save1.getId());

        System.out.println("=====Logic End=====");
        // then
        assertThat(categoryByUser.getCategoryName()).isEqualTo("등1");
    }
}