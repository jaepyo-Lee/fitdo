package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordDto;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyRecordCreateCommand;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExerciseRecordServiceTest {

    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    @Autowired
    private DailyRecordJpaRepository dailyRecordJpaRepository;
    @Autowired
    private RecordService recordService;

    @BeforeEach
    void init() {
        dailyExerciseRecordJpaRepository.deleteAll();
        dailyRecordJpaRepository.deleteAll();
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Nested
    @DisplayName("운동기록기능 테스트")
    class ExerciseExerciseRecordCreateCommandTest {
        @Test
        void 다른사람의기록도저장되어있을때_생성테스트() {
            UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser = userJpaRepository.save(user);

            CategoryJpaEntity category = CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);


            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));

            DailyRecordCreateCommand command = new DailyRecordCreateCommand(today, saveExercise.getId(), dailyExerciseRecordDtos);

            recordService.writeDailyExerciseFrom(saveUser.getId(), List.of(command));


            // when
            System.out.println("=====Logic Start=====");
            UserJpaEntity user2 = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser2 = userJpaRepository.save(user2);
            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));

            DailyRecordCreateCommand newCommand = new DailyRecordCreateCommand(today, saveExercise.getId(), newDailyExerciseRecordDtos);

            recordService.writeDailyExerciseFrom(saveUser2.getId(), List.of(newCommand));

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(5);
        }

        @Test
        void 이미기록됐던운동기록에대한_재생성테스트() {
            // given
            UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser = userJpaRepository.save(user);

            CategoryJpaEntity category = CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);


            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));

            DailyRecordCreateCommand command = new DailyRecordCreateCommand(today, saveExercise.getId(), dailyExerciseRecordDtos);

            recordService.writeDailyExerciseFrom(saveUser.getId(), List.of(command));


            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));

            DailyRecordCreateCommand newCommand = new DailyRecordCreateCommand(today, saveExercise.getId(), newDailyExerciseRecordDtos);

            recordService.writeDailyExerciseFrom(saveUser.getId(), List.of(newCommand));

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(2);

        }

        @Test
        void 처음기록되는운동기록에대한_생성테스트() {
            // given
            UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser = userJpaRepository.save(user);

            CategoryJpaEntity category = CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);

            // when
            System.out.println("=====Logic Start=====");
            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));

            DailyRecordCreateCommand command = new DailyRecordCreateCommand(today, saveExercise.getId(), dailyExerciseRecordDtos);

            recordService.writeDailyExerciseFrom(saveUser.getId(), List.of(command));

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(3);

        }
    }

}