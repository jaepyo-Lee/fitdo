package com.jaejoo.fitdomysql.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.core.Exercise;
import com.jaejoo.fitdomysql.domain.exercise.core.ExerciseRecord;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.DailyRecordCommandJpaRepositoryImpl;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import(DailyRecordCommandJpaRepositoryImpl.class)
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


        CategoryJpaEntity category = categoryJpaRepository.save(CategoryJpaEntity.builder().part(BodyPart.CHEST).build());


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

        recordCommandRepository.deleteDateRecordOf(saveUser.getId(), todayDate);

        System.out.println("=====Logic End=====");
        // then
        List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
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


        CategoryJpaEntity category = categoryJpaRepository.save(CategoryJpaEntity.builder().part(BodyPart.CHEST).build());


        ExerciseJpaEntity exercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .category(category)
                .name("벤치프레스")
                .build());


        // when
        System.out.println("=====Logic Start=====");

        ExerciseRecord exerciseRecord1 = new ExerciseRecord(20, 5, 1, true);
        ExerciseRecord exerciseRecord2 = new ExerciseRecord(20, 5, 2, false);
        Exercise exerciseRecords = new Exercise(List.of(exerciseRecord1, exerciseRecord2));

        recordCommandRepository.saveAll(saveUser.getId(), exercise.getId(), todayDate, exerciseRecords);

        System.out.println("=====Logic End=====");
        // then
        List<DailyExerciseRecordJpaEntity> all = dailyExerciseRecordJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

    }
}