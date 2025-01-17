package com.jaejoo.fitdomysql.exercise.infra.repository;

import com.jaejoo.fitdomysql.config.QuerydslTestConfig;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.*;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
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
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@Import({QuerydslTestConfig.class})
@DataJpaTest
class RecordQueryRepositoryTest {

    @Autowired
    private RecordQueryRepository repository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    CategoryJpaRepository categoryRepository;
    @Autowired
    ExerciseJpaRepository exerciseRepository;
    @Autowired
    DailyJpaRepository dailyRecordRepository;
    @Autowired
    DailyExerciseJpaRepository dailyExerciseJpaRepository;

    @Autowired
    ExerciseSetJpaRepository exerciseSetJpaRepository;


    @Nested
    @DisplayName("사용자가 특정년월에 진행한 운동일자 조회")
    class findDailyRecordInYearMonth {
        @Test
        void 특정월에속한모든운동일자조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.of(2024, 2, 1);
            DailyJpaEntity dailyJpaEntity = new DailyJpaEntity(today, saveUser);
            DailyJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyJpaEntity);
            DailyExerciseJpaEntity dailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity dailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            DailyExerciseJpaEntity save2 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save1).build());
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save2).build());

            //어제날짜
            LocalDate yesterday = LocalDate.of(2024, 1, 31);
            DailyJpaEntity yesterdayDailyJpaEntity = new DailyJpaEntity(yesterday, saveUser);
            DailyJpaEntity saveYesterdayDailyRecord = dailyRecordRepository.save(yesterdayDailyJpaEntity);

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save3 = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity);

            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save3).build());

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save4 = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save4).build());

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity3 = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise2)
                    .build();
            DailyExerciseJpaEntity save5 = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity3);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save5).build());

            // when
            System.out.println("=====Logic Start=====");

            List<DailyJpaEntity> dailyRecordsByUserAndYearMonth = repository.findDailyRecordsByUserAndYearMonth(saveUser.getId(), YearMonth.of(2024, 2));

            System.out.println("=====Logic End=====");
            // then
            assertThat(dailyRecordsByUserAndYearMonth.size()).isOne();
        }
    }

    @Nested
    @DisplayName("exercise를 통해 운동기록조회")
    class findExerciseRecordsByExerciseId {
        @Test
        void success() {
            //given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            LocalDate one = LocalDate.of(2024, 1, 31);
            DailyJpaEntity dailyJpaEntity = new DailyJpaEntity(one, saveUser);
            DailyJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyJpaEntity);

            DailyExerciseJpaEntity dailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity dailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            DailyExerciseJpaEntity save2 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save1).build());
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save2).build());

            LocalDate two = LocalDate.of(2024, 2, 2);
            DailyJpaEntity twodailyJpaEntity = new DailyJpaEntity(two, saveUser);
            DailyJpaEntity twosaveDailyRecord = dailyRecordRepository.save(twodailyJpaEntity);


            DailyExerciseJpaEntity twodailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(twosaveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity twodailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(twosaveDailyRecord).exercise(saveExercise)
                    .build();

            DailyExerciseJpaEntity save3 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            DailyExerciseJpaEntity save4 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save3).build());
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save4).build());

            dailyExerciseJpaRepository.save(twodailyExerciseJpaEntity);
            dailyExerciseJpaRepository.save(twodailyExerciseJpaEntity2);

            //when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseJpaEntity> exerciseRecordsByExercise = repository.findExerciseRecordsInDailyRecordDividedBy(saveExercise, saveDailyRecord);

            System.out.println("=====Logic End=====");
            //then
            assertThat(exerciseRecordsByExercise.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("해당된 날짜에 진행한 운동목록 조회테스트")
    class findExercisesByDailyRecordTest {
        @Test
        void 해당날짜의운동목록조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            LocalDate date = LocalDate.now();
            DailyJpaEntity dailyJpaEntity = new DailyJpaEntity(date, saveUser);
            DailyJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyJpaEntity);

            DailyExerciseJpaEntity dailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity dailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            DailyExerciseJpaEntity save2 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save1).build());
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save2).build());

            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseJpaEntity> exercisesByDailyRecord = repository.findExercisesByDailyRecord(saveDailyRecord);

            System.out.println("=====Logic End=====");
            // then
            assertThat(exercisesByDailyRecord.size()).isEqualTo(1);
        }

        @Test
        void 여러날짜의기록이존재할때_해당날짜의운동목록조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.now();
            DailyJpaEntity dailyJpaEntity = new DailyJpaEntity(today, saveUser);
            DailyJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyJpaEntity);
            DailyExerciseJpaEntity dailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity dailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save1 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity);
            DailyExerciseJpaEntity save2 = dailyExerciseJpaRepository.save(dailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save1).build());
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save2).build());


            //어제날짜
            LocalDate yesterday = LocalDate.now();
            DailyJpaEntity yesterdayDailyJpaEntity = new DailyJpaEntity(yesterday, saveUser);
            DailyJpaEntity saveYesterdayDailyRecord = dailyRecordRepository.save(yesterdayDailyJpaEntity);

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save).build());

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity2 = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise)
                    .build();
            DailyExerciseJpaEntity save3 = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity2);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save3).build());

            DailyExerciseJpaEntity yesterdayDailyExerciseJpaEntity3 = DailyExerciseJpaEntity.builder()
                    .daily(saveYesterdayDailyRecord).exercise(saveExercise2)
                    .build();
            DailyExerciseJpaEntity save4 = dailyExerciseJpaRepository.save(yesterdayDailyExerciseJpaEntity3);
            exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(true).dailyExercise(save4).build());


            // when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseJpaEntity> yesterdayExercisesByDailyRecord = repository.findExercisesByDailyRecord(saveYesterdayDailyRecord);
            List<DailyExerciseJpaEntity> exercisesByDailyRecord = repository.findExercisesByDailyRecord(saveDailyRecord);
            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(exercisesByDailyRecord.size()).isEqualTo(1),
                    () -> assertThat(yesterdayExercisesByDailyRecord.size()).isEqualTo(2));
        }
    }
}
