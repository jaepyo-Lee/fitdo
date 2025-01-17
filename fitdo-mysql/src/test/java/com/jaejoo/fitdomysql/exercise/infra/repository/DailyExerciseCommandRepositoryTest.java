package com.jaejoo.fitdomysql.exercise.infra.repository;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.DailyExerciseCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordCommandRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.DailyDailyExerciseCommandJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.impl.DailyRecordCommandJpaRepositoryImpl;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.*;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Import(DailyDailyExerciseCommandJpaRepository.class)
@DataJpaTest
class DailyExerciseCommandRepositoryTest {
    @Autowired
    private DailyExerciseCommandRepository dailyExerciseCommandRepository;
    @Autowired
    private DailyExerciseJpaRepository dailyExerciseJpaRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DailyJpaRepository dailyJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private ExerciseSetJpaRepository exerciseSetJpaRepository;

    @BeforeEach
    void init() {
        exerciseSetJpaRepository.deleteAll();
        dailyExerciseJpaRepository.deleteAll();
        dailyJpaRepository.deleteAll();
        exerciseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Test
    void 사용자의_특정날의운동기록정보_삭제() {
        // given
        UserJpaEntity user = UserJpaEntity.builder()
                .authId("authId").authType(AuthType.KAKAO).username("username").weight(80).height(180).newFlag(true).role(GrantRole.ROLE_USER)
                .build();

        UserJpaEntity saveUser = userJpaRepository.save(user);
        LocalDate todayDate = LocalDate.now();
        DailyJpaEntity dailyRecord = new DailyJpaEntity(todayDate, saveUser);
        DailyJpaEntity saveDailyRecord = dailyJpaRepository.save(dailyRecord);

        CategoryJpaEntity category = categoryJpaRepository.save(CategoryJpaEntity.builder().part(BodyPart.CHEST).build());

        ExerciseJpaEntity exercise = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .user(saveUser)
                .category(category)
                .name("벤치프레스")
                .build());

        DailyExerciseJpaEntity saveDailyExercise = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, exercise));
        exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(10, 10, 1, true, saveDailyExercise));

        ExerciseJpaEntity exercise2 = exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .user(saveUser)
                .category(category)
                .build());

        DailyExerciseJpaEntity save = dailyExerciseJpaRepository.save(new DailyExerciseJpaEntity(saveDailyRecord, exercise2));
        exerciseSetJpaRepository.save(new ExerciseSetJpaEntity(10, 10, 1, true, save));

        // when
        System.out.println("=====Logic Start=====");
        exerciseSetJpaRepository.deleteAllByDailyExercise(save.getId());
        exerciseSetJpaRepository.deleteAllByDailyExercise(saveDailyExercise.getId());

        // Ensure exercise sets are deleted
        System.out.println("After exerciseSet deletion: " + exerciseSetJpaRepository.findAll().size());

        dailyExerciseCommandRepository.deleteDateRecordOf(saveUser.getId(), todayDate);

        System.out.println("=====Logic End=====");

        // then
        List<DailyExerciseJpaEntity> all = dailyExerciseJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}