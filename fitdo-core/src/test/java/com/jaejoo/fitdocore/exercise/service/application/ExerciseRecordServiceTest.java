package com.jaejoo.fitdocore.exercise.service.application;


import com.jaejoo.fitdocore.exercise.ExerciseRecordService;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordCreateCommand;
import com.jaejoo.fitdocore.exercise.req.DailyExerciseRecordDto;
import com.jaejoo.fitdocore.exercise.req.RecordExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
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
import java.time.YearMonth;
import java.util.List;

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
    @DisplayName("조회월의 운동진행여부 퍼센티지 조회")
    class calculateProgressPercentageInMonth {
        @Test
        void 데이터가없는날0퍼센트조회되는지확인() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate, saveUser));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            //6 3
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> result = exerciseRecordService.readProgressPercentage(saveUser.getId(), YearMonth.of(2024, 12));

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(result.size()).isEqualTo(31),
                    () -> assertThat(result.get(0).getDate()).isEqualTo(LocalDate.of(2024, 12, 1)),
                    () -> assertThat(result.get(0).getPercentage()).isEqualTo(0.0));
        }

        @Test
        void 조회월의진행퍼센티지조회() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate, saveUser));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 3, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 4, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            //6 3
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> result = exerciseRecordService.readProgressPercentage(saveUser.getId(), YearMonth.of(2024, 12));

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
                    () -> assertThat(exerciseRecordsOfUserInMonth.getExerciseDate()).isEqualTo(today));
        }
    }
}