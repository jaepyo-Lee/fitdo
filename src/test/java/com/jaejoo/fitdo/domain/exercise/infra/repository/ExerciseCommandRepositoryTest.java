package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.ExerciseCommandJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import({ExerciseCommandJpaRepository.class})
@DataJpaTest
class ExerciseCommandRepositoryTest {
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private ExerciseCommandRepository exerciseCommandRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;

    /*@BeforeEach
    void init(){
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
    }*/

    @Test
    void 운동저장() {
        // given
        CategoryJpaEntity categoryJpaEntity = new CategoryJpaEntity();
        CategoryJpaEntity saveCategory = categoryJpaRepository.save(categoryJpaEntity);
        ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().category(saveCategory).name("name").build();

        // when
        System.out.println("=====Logic Start=====");

        ExerciseJpaEntity save = exerciseCommandRepository.save(exerciseJpaEntity);

        System.out.println("=====Logic End=====");
        // then
        assertThat(save.getName()).isEqualTo("name");

    }
}