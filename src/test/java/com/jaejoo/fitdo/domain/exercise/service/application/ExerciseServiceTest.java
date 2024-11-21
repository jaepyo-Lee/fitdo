package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.ExerciseCreateCommand;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ExerciseServiceTest {
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;

/*    @AfterEach
    void init(){
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }*/

    @Test
    void 운동목록생성() {
        // given
        UserJpaEntity user= UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity user2= UserJpaEntity.builder().build();
        UserJpaEntity saveUser2 = userJpaRepository.save(user2);

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().user(saveUser).categoryName("가슴").build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity chestCategory2 = CategoryJpaEntity.builder().user(saveUser2).categoryName("가슴").build();
        CategoryJpaEntity saveChestCategory2 = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().user(saveUser).categoryName("등").build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(chestCategory);

        // when
        System.out.println("=====Logic Start=====");

        String name = "벤치프레스";
        String actual = exerciseService.createExercise(new ExerciseCreateCommand(saveChestCategory.getId(), name));

        System.out.println("=====Logic End=====");
        // then
        assertThat(actual).isEqualTo(name);

    }
}