package com.jaejoo.fitdocore.exercise.service.application;


import com.jaejoo.fitdocore.exercise.ExerciseRecordService;
import com.jaejoo.fitdocore.exercise.res.FindMonthExerciseRecords;
import com.jaejoo.fitdocore.exercise.res.ProgressPercentage;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.*;
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
    private DailyExerciseJpaRepository dailyExerciseJpaRepository;
    @Autowired
    private DailyJpaRepository dailyJpaRepository;
    @Autowired
    private ExerciseRecordService exerciseRecordService;
    @Autowired
    private ExerciseSetJpaRepository exerciseSetJpaRepository;

    // 공통 데이터 생성 메서드
    private UserJpaEntity createTestUser() {
        return userJpaRepository.save(new UserJpaEntity());
    }

    private CategoryJpaEntity createTestCategory(BodyPart bodyPart) {
        return categoryJpaRepository.save(new CategoryJpaEntity(bodyPart));
    }

    private ExerciseJpaEntity createTestExercise(String name, UserJpaEntity user, CategoryJpaEntity category) {
        return exerciseJpaRepository.save(
                ExerciseJpaEntity.builder()
                        .name(name)
                        .deleteDelimiter(DeleteDelimiter.IN_USER)
                        .category(category)
                        .user(user)
                        .build()
        );
    }

    private DailyJpaEntity createDailyRecord(UserJpaEntity user, LocalDate date) {
        return dailyJpaRepository.save(new DailyJpaEntity(date, user));
    }

    private DailyExerciseJpaEntity createDailyExercise(DailyJpaEntity daily, ExerciseJpaEntity exercise) {
        return dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(daily, exercise));
    }

    private void createExerciseSet(int weight, int volume, int number, boolean done, DailyExerciseJpaEntity dailyExercise) {
        exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(weight, number, volume, done, dailyExercise));
    }

    @Nested
    @DisplayName("운동 진행 퍼센티지 조회")
    class CalculateProgressPercentageInMonth {

        @Test
        void 데이터가없는날Null퍼센트조회() {
            // given
            UserJpaEntity user = createTestUser();
            CategoryJpaEntity category = createTestCategory(BodyPart.BACK);
            ExerciseJpaEntity exercise = createTestExercise("exercise", user, category);

            LocalDate targetDate = LocalDate.of(2024, 12, 5);
            DailyJpaEntity dailyRecord = createDailyRecord(user, targetDate);

            DailyExerciseJpaEntity dailyExercise1 = createDailyExercise(dailyRecord, exercise);
            createExerciseSet(12, 5, 1, false, dailyExercise1);

            DailyExerciseJpaEntity dailyExercise2 = createDailyExercise(dailyRecord, exercise);
            createExerciseSet(12, 1, 2, true, dailyExercise2);

            // when
            List<ProgressPercentage> result = exerciseRecordService.readProgressPercentage(user.getId(), YearMonth.of(2024, 12));

            // then
            long nullCount = result.stream().filter(r -> r.getPercentage() == null).count();
            long notNullCount = result.stream().filter(r -> r.getPercentage() != null).count();

            assertAll(
                    () -> assertThat(result.size()).isEqualTo(31),
                    () -> assertThat(result.get(0).getDate()).isEqualTo(LocalDate.of(2024, 12, 1)),
                    () -> assertThat(result.get(0).getPercentage()).isNull(),
                    () -> assertThat(notNullCount).isEqualTo(1),
                    () -> assertThat(nullCount).isEqualTo(30)
            );
        }

        @Test
        void 특정월의진행퍼센티지조회() {
            // given
            UserJpaEntity user = createTestUser();
            CategoryJpaEntity category = createTestCategory(BodyPart.BACK);
            ExerciseJpaEntity exercise = createTestExercise("exercise", user, category);

            LocalDate targetDate = LocalDate.of(2024, 12, 5);
            DailyJpaEntity dailyRecord = createDailyRecord(user, targetDate);

            createExerciseSet(1, 1, 1, false, createDailyExercise(dailyRecord, exercise));
            createExerciseSet(1, 2, 2, false, createDailyExercise(dailyRecord, exercise));
            createExerciseSet(1, 3, 3, true, createDailyExercise(dailyRecord, exercise));
            createExerciseSet(1, 4, 4, true, createDailyExercise(dailyRecord, exercise));
            createExerciseSet(1, 5, 5, false, createDailyExercise(dailyRecord, exercise));
            createExerciseSet(1, 1, 6, true, createDailyExercise(dailyRecord, exercise));

            // when
            List<ProgressPercentage> result = exerciseRecordService.readProgressPercentage(user.getId(), YearMonth.of(2024, 12));

            // then
            assertAll(
                    () -> assertThat(result.size()).isEqualTo(31),
                    () -> assertThat(result.get(4).getDate()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(result.get(4).getPercentage()).isEqualTo(50.0)
            );
        }
    }

    @Nested
    @DisplayName("특정 월의 운동 기록 조회")
    class FindSpecificMonthExerciseTest {

        @Test
        void 특정월의운동기록조회() {
            // given
            UserJpaEntity user = createTestUser();
            CategoryJpaEntity category = createTestCategory(BodyPart.CHEST);

            ExerciseJpaEntity exercise1 = createTestExercise("벤치프레스", user, category);
            ExerciseJpaEntity exercise2 = createTestExercise("플라이", user, category);

            LocalDate today = LocalDate.of(2024, 2, 1);
            DailyJpaEntity dailyRecord = createDailyRecord(user, today);

            createExerciseSet(1, 10,1, true, createDailyExercise(dailyRecord, exercise1));
            createDailyExercise(dailyRecord, exercise2);

            // when
            FindMonthExerciseRecords result = exerciseRecordService.findExerciseRecordsOfUserAtDate(user.getId(), today);

            // then
            assertAll(
                    () -> assertThat(result.getRecords().size()).isEqualTo(2),
                    () -> assertThat(result.getRecords().get(0).getSets().size()).isEqualTo(1),
                    () -> assertThat(result.getRecords().get(1).getSets().size()).isEqualTo(0),
                    () -> assertThat(result.getDate()).isEqualTo(today)
            );
        }
    }
}