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

    @Nested
    @DisplayName("조회월의 운동진행여부 퍼센티지 조회")
    class calculateProgressPercentageInMonth {
        @Test
        void 데이터가없는날null퍼센트조회되는지확인() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            DailyJpaEntity saveDailyRecord = dailyJpaRepository.save(new DailyJpaEntity(localDate, saveUser));
            DailyExerciseJpaEntity save = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 5, false, save));
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 1, true, save1));
            //6 3
            // when
            System.out.println("=====Logic Start=====");

            List<ProgressPercentage> result = exerciseRecordService.readProgressPercentage(saveUser.getId(), YearMonth.of(2024, 12));

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(result.size()).isEqualTo(31),
                    () -> assertThat(result.get(0).getDate()).isEqualTo(LocalDate.of(2024, 12, 1)),
                    () -> assertThat(result.get(0).getPercentage()).isNull());
        }

        @Test
        void 조회월의진행퍼센티지조회() {
            // given
            UserJpaEntity saveUser = userJpaRepository.save(new UserJpaEntity());
            CategoryJpaEntity saveCategory = categoryJpaRepository.save(new CategoryJpaEntity(BodyPart.BACK));
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder().name("exercise").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).user(saveUser).build());

            LocalDate localDate = LocalDate.of(2024, 12, 5);
            DailyJpaEntity saveDailyRecord = dailyJpaRepository.save(new DailyJpaEntity(localDate, saveUser));
            DailyExerciseJpaEntity save = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 1, false, save));

            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 2, false, save1));

            DailyExerciseJpaEntity save2 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 3, true, save2));

            DailyExerciseJpaEntity save3 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 4, true, save3));

            DailyExerciseJpaEntity save4 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 5, false, save4));

            DailyExerciseJpaEntity save5 = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, saveExercise));
            exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(1, 1, 1, true, save5));

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

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().user(saveUser).name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().user(saveUser).name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseJpaRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.of(2024, 2, 1);
            DailyJpaEntity dailyJpaEntity = new DailyJpaEntity(today, saveUser);
            DailyJpaEntity saveDailyRecord = dailyJpaRepository.save(dailyJpaEntity);

            DailyExerciseJpaEntity dailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder()
                    .number(1).volume(10).done(true).dailyExercise(save)
                    .build());

            DailyExerciseJpaEntity dailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise2)
                    .build();
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);

            // when
            System.out.println("=====Logic Start=====");

            FindMonthExerciseRecords exerciseRecordsOfUserInMonth = exerciseRecordService.findExerciseRecordsOfUserAtDate(saveUser.getId(), today);

            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(exerciseRecordsOfUserInMonth.getRecords().size()).isEqualTo(2),
                    () -> assertThat(exerciseRecordsOfUserInMonth.getRecords().get(0).getSets().size()).isEqualTo(1),
                    () -> assertThat(exerciseRecordsOfUserInMonth.getRecords().get(1).getSets().size()).isEqualTo(0),
                    () -> assertThat(exerciseRecordsOfUserInMonth.getDate()).isEqualTo(today));
        }
    }
}