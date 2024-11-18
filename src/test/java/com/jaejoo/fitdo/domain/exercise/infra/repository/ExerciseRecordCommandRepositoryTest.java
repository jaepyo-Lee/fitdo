package com.jaejoo.fitdo.domain.exercise.infra.repository;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdo.domain.exercise.core.ExerciseRecords;
import com.jaejoo.fitdo.domain.exercise.infra.repository.impl.DailyRecordCommandRepositoryImpl;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(DailyRecordCommandRepositoryImpl.class)
@DataJpaTest
class ExerciseRecordCommandRepositoryTest {
    @Autowired
    private RecordCommandRepository recordCommandRepository;
    @Autowired
    private DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DailyRecordJpaRepository dailyRecordJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @BeforeEach
    void init(){
        dailyExerciseRecordJpaRepository.deleteAll();
        dailyRecordJpaRepository.deleteAll();
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Test
    void 사용자의특정날의운동기록정보삭제() {
        // given
        UserJpaEntity user = UserJpaEntity.builder()
                .authId("authId").authType(AuthType.KAKAO).username("username").weight(80).height(180).newFlag(true).role(GrantRole.ROLE_USER)
                .build();


        UserJpaEntity saveUser = userJpaRepository.save(user);

        LocalDate todayDate = LocalDate.now();

        DailyRecordJpaEntity dailyRecord = new DailyRecordJpaEntity(todayDate, saveUser);

        DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(dailyRecord);


        CategoryJpaEntity category = categoryJpaRepository.save(CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build());


        ExerciseJpaEntity exercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .category(category)
                .name("벤치프레스")
                .build());

        dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(10, 10, 1, true, saveDailyRecord, exercise));

        ExerciseJpaEntity exercise2 = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .category(category)
                .name("플라이머신")
                .build());

        dailyExerciseRecordJpaRepository.save(new DailyExerciseRecordJpaEntity(10, 10, 1, true, saveDailyRecord, exercise2));


        // when
        System.out.println("=====Logic Start=====");

        recordCommandRepository.deleteDateRecordOf(saveUser.getId(), exercise.getId(), todayDate);

        System.out.println("=====Logic End=====");
        // then
        List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void 사용자운동기록저장() {
        // given
        UserJpaEntity user = UserJpaEntity.builder()
                .authId("authId").authType(AuthType.KAKAO).username("username").weight(80).height(180).newFlag(true).role(GrantRole.ROLE_USER)
                .build();


        UserJpaEntity saveUser = userJpaRepository.save(user);

        LocalDate todayDate = LocalDate.now();

        DailyRecordJpaEntity dailyRecord = new DailyRecordJpaEntity(todayDate, saveUser);

        DailyRecordJpaEntity saveDailyRecord = dailyRecordJpaRepository.save(dailyRecord);


        CategoryJpaEntity category = categoryJpaRepository.save(CategoryJpaEntity.builder().categoryName("가슴").user(saveUser).build());


        ExerciseJpaEntity exercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .category(category)
                .name("벤치프레스")
                .build());


        // when
        System.out.println("=====Logic Start=====");

        ExerciseRecord exerciseRecord1 = new ExerciseRecord(20, 5, 1, true);
        ExerciseRecord exerciseRecord2 = new ExerciseRecord(20, 5, 2, false);
        ExerciseRecords exerciseRecords = new ExerciseRecords(List.of(exerciseRecord1, exerciseRecord2));

        recordCommandRepository.saveAll(saveUser.getId(),exercise.getId(),todayDate, exerciseRecords);

        System.out.println("=====Logic End=====");
        // then
        List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

    }
}