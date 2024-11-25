package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.ExerciseQueryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(ExerciseQueryJpaRepository.class)
@DataJpaTest
class ExerciseQueryRepositoryTest {
    @Autowired
    private ExerciseQueryRepository exerciseQueryRepository;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    CategoryJpaRepository categoryRepository;
    @Autowired
    ExerciseJpaRepository exerciseRepository;


    @Test
    void 아이디리스트를_해당되는_운동_전체조회() {
        // given
        UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
        UserJpaEntity saveUser = userRepository.save(userJpaEntity);

        CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
        ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

        ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
        ExerciseJpaEntity saveExercise2 = exerciseRepository.save(exerciseJpaEntity2);

        // when
        System.out.println("=====Logic Start=====");

        List<ExerciseJpaEntity> exercises = exerciseQueryRepository.findAllByIds(List.of(saveExercise.getId(),saveExercise2.getId()));

        System.out.println("=====Logic End=====");
        // then
        assertThat(exercises.size()).isEqualTo(2);

    }
}