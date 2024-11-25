package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.config.QuerydslTestConfig;
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
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
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
    DailyRecordJpaRepository dailyRecordRepository;
    @Autowired
    DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;


/*    @BeforeEach
    void init() {
        dailyExerciseRecordJpaRepository.deleteAll();
        dailyRecordRepository.deleteAll();
        exerciseRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }*/

    @Nested
    @DisplayName("사용자가 특정년월에 진행한 운동일자 조회")
    class findDailyRecordInYearMonth {
        @Test
        void 특정월에속한모든운동일자조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.of(2024, 2, 1);
            DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(today, saveUser);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyRecordJpaEntity);
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
            DailyRecordJpaEntity saveYesterdayDailyRecord = dailyRecordRepository.save(yesterdayDailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity);

            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity2);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity3 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise2)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity3);

            // when
            System.out.println("=====Logic Start=====");

            List<DailyRecordJpaEntity> dailyRecordsByUserAndYearMonth = repository.findDailyRecordsByUserAndYearMonth(saveUser.getId(), YearMonth.of(2024, 2));

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

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            LocalDate one = LocalDate.of(2024, 1, 31);
            DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(one, saveUser);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyRecordJpaEntity);

            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity);
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity2);

            LocalDate two = LocalDate.of(2024, 2, 2);
            DailyRecordJpaEntity twodailyRecordJpaEntity = new DailyRecordJpaEntity(two, saveUser);
            DailyRecordJpaEntity twosaveDailyRecord = dailyRecordRepository.save(twodailyRecordJpaEntity);

            DailyExerciseRecordJpaEntity twodailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(twosaveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            DailyExerciseRecordJpaEntity twodailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(twosaveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(twodailyExerciseRecordJpaEntity);
            dailyExerciseRecordJpaRepository.save(twodailyExerciseRecordJpaEntity2);

            //when
            System.out.println("=====Logic Start=====");

            List<DailyExerciseRecordJpaEntity> exerciseRecordsByExercise = repository.findExerciseRecordsInDailyRecordDividedBy(saveExercise,saveDailyRecord);

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

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            LocalDate date = LocalDate.now();
            DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(date, saveUser);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyRecordJpaEntity);

            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity);
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity2);

            // when
            System.out.println("=====Logic Start=====");

            List<ExerciseJpaEntity> exercisesByDailyRecord = repository.findExercisesByDailyRecord(saveDailyRecord);

            System.out.println("=====Logic End=====");
            // then
            assertThat(exercisesByDailyRecord.size()).isEqualTo(1);
        }

        @Test
        void 여러날짜의기록이존재할때_해당날짜의운동목록조회() {
            // given
            UserJpaEntity userJpaEntity = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_USER);
            UserJpaEntity saveUser = userRepository.save(userJpaEntity);

            CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
            CategoryJpaEntity saveCategory = categoryRepository.save(categoryJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity = ExerciseJpaEntity.builder().name("벤치프레스").category(saveCategory).build();
            ExerciseJpaEntity saveExercise = exerciseRepository.save(exerciseJpaEntity);

            ExerciseJpaEntity exerciseJpaEntity2 = ExerciseJpaEntity.builder().name("플라이").category(saveCategory).build();
            ExerciseJpaEntity saveExercise2 = exerciseRepository.save(exerciseJpaEntity2);


            //오늘날짜
            LocalDate today = LocalDate.now();
            DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(today, saveUser);
            DailyRecordJpaEntity saveDailyRecord = dailyRecordRepository.save(dailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity);
            dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity2);


            //어제날짜
            LocalDate yesterday = LocalDate.now();
            DailyRecordJpaEntity yesterdayDailyRecordJpaEntity = new DailyRecordJpaEntity(yesterday, saveUser);
            DailyRecordJpaEntity saveYesterdayDailyRecord = dailyRecordRepository.save(yesterdayDailyRecordJpaEntity);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity);

            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity2 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity2);
            DailyExerciseRecordJpaEntity yesterdayDailyExerciseRecordJpaEntity3 = DailyExerciseRecordJpaEntity.builder()
                    .dailyRecord(saveYesterdayDailyRecord).exerciseSet(1).volume(10).isProgress(true).exercise(saveExercise2)
                    .build();
            dailyExerciseRecordJpaRepository.save(yesterdayDailyExerciseRecordJpaEntity3);


            // when
            System.out.println("=====Logic Start=====");

            List<ExerciseJpaEntity> yesterdayExercisesByDailyRecord = repository.findExercisesByDailyRecord(saveYesterdayDailyRecord);
            List<ExerciseJpaEntity> exercisesByDailyRecord = repository.findExercisesByDailyRecord(saveDailyRecord);
            System.out.println("=====Logic End=====");
            // then
            assertAll(() -> assertThat(exercisesByDailyRecord.size()).isEqualTo(1),
                    () -> assertThat(yesterdayExercisesByDailyRecord.size()).isEqualTo(2));
        }
    }
}
