package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.config.QuerydslTestConfig;
import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.dto.ProgressInDateDto;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
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
class ExerciseRecordQueryRepositoryTest {
    @Autowired
    ExerciseRecordQueryRepository exerciseRecordQueryRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    @Autowired
    ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;
    @Autowired
    DailyRecordJpaRepository dailyRecordJpaRepository;

    @Nested
    @DisplayName("findAllProgressInMonthOfUser")
    class findAllProgressInMonthOfUser {
        @Test
        void 해당되는월에_포함되는_운동진행기록_조회() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate, saveUser));
            DailyRecordJpaEntity yesterdaySaveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate.minusDays(1L), saveUser));
            DailyRecordJpaEntity tomorrowSaveDailyRecord = dailyRecordJpaRepository.save(new DailyRecordJpaEntity(localDate.plusDays(1L), saveUser));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 3, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 4, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, tomorrowSaveDailyRecord, saveExercise));

            // when
            System.out.println("=====Logic Start=====");

            List<ProgressInDateDto> response = exerciseRecordQueryRepository.findAllProgressInMonthOfUser(saveUser.getId(), YearMonth.of(2024, 12));

            System.out.println("=====Logic End=====");
            // then
            assertThat(response.size()).isEqualTo(9);

        }

        @Test
        void 해당되는월외는_운동진행기록_조회안함() {
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
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 3, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 4, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, tomorrowSaveDailyRecord, saveExercise));

            // when
            System.out.println("=====Logic Start=====");

            List<ProgressInDateDto> response = exerciseRecordQueryRepository.findAllProgressInMonthOfUser(saveUser.getId(), YearMonth.of(2024, 12));

            System.out.println("=====Logic End=====");
            // then
            assertThat(response.size()).isEqualTo(8);
        }
    }

    @Nested
    @DisplayName("findExerciseRecordAtDate")
    class findExerciseRecordAtDateTest{
        @Test
        void 특정날의_운동기록_조회() {
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
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 3, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 4, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 5, false, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, yesterdaySaveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 1, true, saveDailyRecord, saveExercise));
            dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(1, 1, 2, true, tomorrowSaveDailyRecord, saveExercise));

            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordJpaEntity> response = exerciseRecordQueryRepository.findExerciseRecordAtDate(saveUser.getId(), localDate);

            System.out.println("=====Logic End=====");
            // then
            assertThat(response.size()).isEqualTo(6);
        }
    }


}