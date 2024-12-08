package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.req.DailyExerciseRecordDto;
import com.jaejoo.fitdo.domain.exercise.service.application.req.RecordExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindMonthExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.service.application.res.ProgressPercentage;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
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
    private ExerciseRecordService exerciseRecordService;

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

            exerciseRecordService.writeDailyExerciseFrom(saveUser.getId(), command);
            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true),
                    new DailyExerciseRecordDto(3, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(newSaveExercise.getId(), newDailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordService.writeDailyExerciseFrom(user.getId(), newCommand);

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

            exerciseRecordService.writeDailyExerciseFrom(saveUser.getId(), command);


            // when
            System.out.println("=====Logic Start=====");
            UserJpaEntity user2 = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
            UserJpaEntity saveUser2 = userJpaRepository.save(user2);
            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(
                    new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), newDailyExerciseRecordDtos));
            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordService.writeDailyExerciseFrom(saveUser2.getId(), newCommand);

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

            exerciseRecordService.writeDailyExerciseFrom(saveUser.getId(), command);


            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordDto> newDailyExerciseRecordDtos = List.of(new DailyExerciseRecordDto(1, 50, 10, true),
                    new DailyExerciseRecordDto(2, 50, 10, true));
            List<RecordExerciseRecords> newRecordExerciseRecords = List.of(new RecordExerciseRecords(saveExercise.getId(), newDailyExerciseRecordDtos));

            DailyExerciseRecordCreateCommand newCommand = new DailyExerciseRecordCreateCommand(today, newRecordExerciseRecords);

            exerciseRecordService.writeDailyExerciseFrom(saveUser.getId(), newCommand);

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
            exerciseRecordService.writeDailyExerciseFrom(saveUser.getId(), command);

            System.out.println("=====Logic End=====");
            // then
            List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
            assertThat(all.size()).isEqualTo(3);

        }
    }

    @Nested
    @DisplayName("조회월의 운동진행여부 퍼센티지 조회")
    class calculateProgressPercentageInMonth {
        @Test
        void 조회월의진행퍼센티지조회() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            LocalDate beforeMonth7Days = LocalDate.of(2024, 12, 23);
            LocalDate afterMonth7Days = LocalDate.of(2025, 1, 8);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate, saveUser));
            DailyRecordJpaEntity yesterdaySaveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(beforeMonth7Days, saveUser));
            DailyRecordJpaEntity tomorrowSaveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(afterMonth7Days, saveUser));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 3, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 4, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, tomorrowSaveDailyRecord, saveExercise));
            //6 3
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> result = exerciseRecordService.calculateProgressPercentageInMonth(saveUser.getId(), YearMonth.of(2024, 12));

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(result.size()).isEqualTo(31),
                    () -> assertThat(result.get(4).getDate()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(result.get(4).getPercentage()).isEqualTo(50.0));
        }
    }

    @Nested
    @DisplayName("특정월의 운동기록 조회")
    class findSpecificMonthExerciseTest {
        @Test
        void 특정월의_운동기록_조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userJpaRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseJpaRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.of(2024, 2, 1);
            DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(today, saveUser);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(dailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity);
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity2);


            //어제날짜
            LocalDate yesterday = LocalDate.of(2024, 1, 31);
            DailyRecordJpaEntity yesterdayDailyRecordJpaEntity = new DailyRecordJpaEntity(yesterday, saveUser);
            DailyRecordJpaEntity saveYesterdayDailyRecord = dailyRecordJpaRepository.save(yesterdayDailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(3).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity);

            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(4).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity2);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity3 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(5).volume(10).isProgress(true).exercise(saveExercise2)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity3);

            //그저께날짜
            LocalDate twodayago = LocalDate.of(2024, 1, 30);
            DailyRecordJpaEntity twodayagoDailyRecordJpaEntity = new DailyRecordJpaEntity(twodayago, saveUser);
            DailyRecordJpaEntity savetwodayagoDailyRecord = dailyRecordJpaRepository.save(twodayagoDailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity twodayagoDailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(savetwodayagoDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(twodayagoDailyExerciseRecordJpaEntity);

            DailyExerciseRecordJpaEntity twodayagoDailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(savetwodayagoDailyRecord).exerciseSet(2).volume(20).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(twodayagoDailyExerciseRecordJpaEntity2);
            DailyExerciseRecordJpaEntity twodayagoDailyExerciseRecordJpaEntity3 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(savetwodayagoDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise2)
                    .build();
            dailyExerciseRecordJpaRepository.save(twodayagoDailyExerciseRecordJpaEntity3);

            // when
            System.out.println("=====Logic Start=====");

            FindMonthExerciseRecords exerciseRecordsOfUserInMonth = exerciseRecordService.findExerciseRecordsOfUserAtDate(saveUser.getId(), today);

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(exerciseRecordsOfUserInMonth.getDateRecords().size()).isEqualTo(1),
                    () -> assertThat(exerciseRecordsOfUserInMonth.getDateRecords().get(0).getRecords().size()).isEqualTo(2),
                    ()-> assertThat(exerciseRecordsOfUserInMonth.getExerciseDate()).isEqualTo(today));
        }
    }
}