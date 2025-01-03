package com.jaejoo.fitdocore.exercise;

import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordDto;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ExerciseRecordWriteServiceTest {

    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    @Autowired
    private ExerciseRecordWriteService exerciseRecordWriteService;

    @Nested
    @DisplayName("운동기록기능 테스트")
    class ExerciseExerciseRecordCreateCommandTest {
        @Test
        void 기존에_저장된운동을_삭제하고_다른운동들로만_기록을_생성했을때() {
            // given
            UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser = userJpaRepository.save(user);

            CategoryJpaEntity category = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);

            ExerciseJpaEntity newExercise = ExerciseJpaEntity.builder().name("fly machine").category(saveCategory).build();
            ExerciseJpaEntity newSaveExercise = exerciseJpaRepository.save(newExercise);

            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> recordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), dailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand command = new DailyExerciseRecordCreateCommand(today, recordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser.getId(), command);
            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(newSaveExercise.getId(), newDailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(user.getId(), newCommand);

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(3);

        }

        @Test
        void 다른사람의기록도저장되어있을때_생성테스트() {
            UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser = userJpaRepository.save(user);

            CategoryJpaEntity category = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);


            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> recordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), dailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand command = new DailyExerciseRecordCreateCommand(today, recordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser.getId(), command);


            // when
            System.out.println("=====Logic Start=====");
            UserJpaEntity user2 = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser2 = userJpaRepository.save(user2);
            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), newDailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser2.getId(), newCommand);

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

            CategoryJpaEntity category = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);


            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> recordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), dailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand command = new DailyExerciseRecordCreateCommand(today, recordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser.getId(), command);


            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), newDailyExerciseRecordDtos));

            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser.getId(), newCommand);

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

            CategoryJpaEntity category = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);
            ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().name("bench press").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);

            // when
            System.out.println("=====Logic Start=====");
            LocalDate today = LocalDate.now();

            List<DailyExerciseRecordDto> dailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> recordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), dailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand command = new DailyExerciseRecordCreateCommand(today, recordExerciseRecords);
            exerciseRecordWriteService.writeDailyExerciseFrom(saveUser.getId(), command);

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(3);

        }
    }
}