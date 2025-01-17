package com.jaejoo.fitdomysql.exercise.infra.repository;

import com.jaejoo.fitdomysql.config.QuerydslTestConfig;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.ExerciseSetQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import({QuerydslTestConfig.class})
@DataJpaTest
class ExerciseSetQueryRepositoryTest {
    @Autowired
    ExerciseSetQueryRepository exerciseSetQueryRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    DailyExerciseJpaRepository dailyExerciseJpaRepository;
    @Autowired
    ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;
    @Autowired
    DailyJpaRepository dailyJpaRepository;

    @Autowired
    ExerciseSetJpaRepository exerciseSetJpaRepository;

    @Nested
    @DisplayName("findAllProgressInMonthOfUser")
    class findAllProgressInMonthOfUser {
        @Test
        void 해당되는월에_포함되는_운동진행기록_조회() {
            // given
            UserJpaEntity user = createUser();
            ExerciseJpaEntity exercise = createExercise(user);

            LocalDate targetDate = LocalDate.of(2024, 12, 5);
            saveDailyExerciseRecordsForMonth(user, exercise, targetDate);

            // when
            System.out.println("=====Logic Start=====");
            List<ProgressInDateDto> response = exerciseSetQueryRepository.findAllProgress(user.getId(), YearMonth.of(2024, 12));
            System.out.println("=====Logic End=====");

            // then
            assertThat(response.size()).isEqualTo(9);
        }

        @Test
        void 해당되는월외는_운동진행기록_조회안함()  {
            // given
            UserJpaEntity user = createUser();
            ExerciseJpaEntity exercise = createExercise(user);

            LocalDate targetDate = LocalDate.of(2024, 12, 5);
            saveDailyExerciseRecordsWithExtraDays(user, exercise, targetDate);

            // when
            System.out.println("=====Logic Start=====");
            List<ProgressInDateDto> response = exerciseSetQueryRepository.findAllProgress(user.getId(), YearMonth.of(2024, 12));
            System.out.println("=====Logic End=====");

            // then
            assertThat(response.size()).isEqualTo(8);
        }

        private void saveDailyExerciseRecordsForMonth(UserJpaEntity user, ExerciseJpaEntity exercise, LocalDate date) {
            DailyJpaEntity dailyRecord = dailyJpaRepository.save(new DailyJpaEntity(date, user));
            DailyJpaEntity previousDayRecord = dailyJpaRepository.save(new DailyJpaEntity(date.minusDays(1), user));

            for (int i = 0; i < 5; i++) {
                DailyExerciseJpaEntity dailyExercise = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(dailyRecord, exercise));
                exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, i + 1, i % 2 == 0, dailyExercise));
            }

            DailyExerciseJpaEntity extraExercise1 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(previousDayRecord, exercise));
            DailyExerciseJpaEntity extraExercise2 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(dailyRecord, exercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 1, true, extraExercise1));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 2, true, extraExercise2));
        }

        private void saveDailyExerciseRecordsWithExtraDays(UserJpaEntity user, ExerciseJpaEntity exercise, LocalDate date) {
            DailyJpaEntity dailyRecord = dailyJpaRepository.save(new DailyJpaEntity(date, user));
            DailyJpaEntity beforeMonthRecord = dailyJpaRepository.save(new DailyJpaEntity(date.minusDays(15), user));
            DailyJpaEntity afterMonthRecord = dailyJpaRepository.save(new DailyJpaEntity(date.plusDays(15), user));

            for (int i = 0; i < 5; i++) {
                DailyExerciseJpaEntity dailyExercise = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(dailyRecord, exercise));
                exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, i + 1, i % 2 == 0, dailyExercise));
            }

            DailyExerciseJpaEntity extraExercise1 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(beforeMonthRecord, exercise));
            DailyExerciseJpaEntity extraExercise2 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(afterMonthRecord, exercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 1, true, extraExercise1));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 2, true, extraExercise2));
        }

        private UserJpaEntity createUser() {
            return userJpaRepository.save(new UserJpaEntity());
        }

        private ExerciseJpaEntity createExercise(UserJpaEntity user) {
            CategoryJpaEntity category = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            return exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                    .name("exercise")
                    .deleteDelimiter(DeleteDelimiter.IN_USER)
                    .category(category)
                    .user(user)
                    .build());
        }
    }

    @Nested
    @DisplayName("findExerciseRecordAtDate")
    class FindExerciseRecordAtDateTest {

        @Test
        void 특정날의_운동기록_조회() {
            // given
            UserJpaEntity user = createUser();
            ExerciseJpaEntity exercise = createExercise(user);

            LocalDate targetDate = LocalDate.of(2024, 12, 5);
            saveDailyExerciseRecordsForSpecificDate(user, exercise, targetDate);

            // when
            System.out.println("=====Logic Start=====");
            List<ExerciseSetJpaEntity> response = exerciseSetQueryRepository.findExerciseRecordAtDate(user.getId(), targetDate);
            System.out.println("=====Logic End=====");

            // then
            assertThat(response.size()).isEqualTo(6);
        }

        private void saveDailyExerciseRecordsForSpecificDate(UserJpaEntity user, ExerciseJpaEntity exercise, LocalDate date) {
            DailyJpaEntity dailyRecord = dailyJpaRepository.save(new DailyJpaEntity(date, user));

            for (int i = 0; i < 6; i++) {
                DailyExerciseJpaEntity dailyExercise = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(dailyRecord, exercise));
                exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, i + 1, i % 2 == 0, dailyExercise));
            }
        }

        private UserJpaEntity createUser() {
            return userJpaRepository.save(new UserJpaEntity());
        }

        private ExerciseJpaEntity createExercise(UserJpaEntity user) {
            CategoryJpaEntity category = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            return exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                    .name("exercise")
                    .deleteDelimiter(DeleteDelimiter.IN_USER)
                    .category(category)
                    .user(user)
                    .build());
        }
    }


}