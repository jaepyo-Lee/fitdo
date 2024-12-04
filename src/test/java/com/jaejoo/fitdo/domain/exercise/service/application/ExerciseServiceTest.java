package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.*;
import com.jaejoo.fitdo.domain.exercise.service.application.req.ExerciseCreateCommand;
import com.jaejoo.fitdo.domain.exercise.service.application.res.FindExercisesWithCategory;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@SpringBootTest
class ExerciseServiceTest {
    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private DailyRecordJpaRepository dailyRecordJpaRepository;
    @Autowired
    private DailyExerciseRecordJpaRepository dailyExerciseRecordJpaRepository;
    @Autowired
    private RecordQueryRepository recordQueryRepository;
    @Autowired
    private RoutineJpaRepository routineJpaRepository;
    @Autowired
    private ExerciseRoutineJpaRepository exerciseRoutineJpaRepository;
/*    @AfterEach
    void init(){
        exerciseJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }*/

    @Test
    void 운동목록생성() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity user2 = UserJpaEntity.builder().build();
        UserJpaEntity saveUser2 = userJpaRepository.save(user2);

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity chestCategory2 = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory2 = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(chestCategory);

        // when
        System.out.println("=====Logic Start=====");

        String name = "벤치프레스";
        String actual = exerciseService.createExercise(new ExerciseCreateCommand(saveChestCategory.getId(), name));

        System.out.println("=====Logic End=====");
        // then
        assertThat(actual).isEqualTo(name);
    }

    //내부값도 테스트해야함. 현재 테스트 깨짐
    @Test
    void 모든부위의_운동목록_조회() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity user2 = UserJpaEntity.builder().build();
        UserJpaEntity saveUser2 = userJpaRepository.save(user2);

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(backCategory);

        ExerciseJpaEntity benchpress1 = ExerciseJpaEntity.builder().name("벤치프레스").user(saveUser).category(saveChestCategory).build();
        ExerciseJpaEntity flymachine1 = ExerciseJpaEntity.builder().name("플라이머신").user(saveUser).category(saveChestCategory).build();
        ExerciseJpaEntity deadlift1 = ExerciseJpaEntity.builder().name("데드리프트").user(saveUser).category(saveBackCategory).build();

        ExerciseJpaEntity dumbellpress2 = ExerciseJpaEntity.builder().name("덤벨 프레스").user(saveUser2).category(saveChestCategory).build();
        ExerciseJpaEntity pressmachine2 = ExerciseJpaEntity.builder().name("프레스 머신").user(saveUser2).category(saveChestCategory).build();
        exerciseJpaRepository.save(benchpress1);
        exerciseJpaRepository.save(flymachine1);
        exerciseJpaRepository.save(deadlift1);

        exerciseJpaRepository.save(dumbellpress2);
        exerciseJpaRepository.save(pressmachine2);

        // when
        System.out.println("=====Logic Start=====");

        List<FindExercisesWithCategory> exercisesWithCategoryOf = exerciseService.findExercisesWithCategoryOf(saveUser.getId());

        System.out.println("=====Logic End=====");
        // then
        assertThat(exercisesWithCategoryOf.size()).isEqualTo(2);
    }

    @Test
    void 운동삭제해도_완전삭제되지않음() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);


        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(backCategory);

        ExerciseJpaEntity benchpress1 = ExerciseJpaEntity.builder().name("벤치프레스").category(saveChestCategory).build();
        ExerciseJpaEntity flymachine1 = ExerciseJpaEntity.builder().name("플라이머신").category(saveChestCategory).build();
        ExerciseJpaEntity deadlift1 = ExerciseJpaEntity.builder().name("데드리프트").category(saveBackCategory).build();

        ExerciseJpaEntity exercise1 = exerciseJpaRepository.save(benchpress1);
        ExerciseJpaEntity exercise2 = exerciseJpaRepository.save(flymachine1);
        ExerciseJpaEntity exercise3 = exerciseJpaRepository.save(deadlift1);

        // when
        System.out.println("=====Logic Start=====");

        exerciseService.removeExercises(exercise1.getId());

        System.out.println("=====Logic End=====");
        // then
        List<ExerciseJpaEntity> all = exerciseJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void 운동삭제시_기록된_운동은_삭제되지않도록_soft_delete진행() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);


        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(backCategory);

        ExerciseJpaEntity benchpress1 = ExerciseJpaEntity.builder().name("벤치프레스").category(saveChestCategory).build();
        ExerciseJpaEntity flymachine1 = ExerciseJpaEntity.builder().name("플라이머신").category(saveChestCategory).build();
        ExerciseJpaEntity deadlift1 = ExerciseJpaEntity.builder().name("데드리프트").category(saveBackCategory).build();

        ExerciseJpaEntity exercise1 = exerciseJpaRepository.save(benchpress1);
        ExerciseJpaEntity exercise2 = exerciseJpaRepository.save(flymachine1);
        ExerciseJpaEntity exercise3 = exerciseJpaRepository.save(deadlift1);

        LocalDate saveDate = LocalDate.of(2024, 11, 25);
        DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(saveDate, saveUser);
        DailyRecordJpaEntity saveDailyRecordJpaEntity = dailyRecordJpaRepository.save(dailyRecordJpaEntity);

        DailyExerciseRecordJpaEntity dailyExerciseRecordJpaEntity = DailyExerciseRecordJpaEntity.builder().exercise(benchpress1).exerciseSet(1).volume(10).isProgress(false).weight(50).dailyRecord(saveDailyRecordJpaEntity).build();
        dailyExerciseRecordJpaRepository.save(dailyExerciseRecordJpaEntity);
        // when
        System.out.println("=====Logic Start=====");

        exerciseService.removeExercises(benchpress1.getId());

        System.out.println("=====Logic End=====");
        // then
        List<DailyExerciseRecordJpaEntity> exerciseRecordsInDailyRecordDividedBy = recordQueryRepository.findExerciseRecordsInDailyRecordDividedBy(benchpress1, saveDailyRecordJpaEntity);
        assertThat(exerciseRecordsInDailyRecordDividedBy.size()).isOne();
    }

    @Test
    void 운동삭제시_같은운동이_여러개_포함된_루틴에_저장된목록은_지워지도록_hard_delete진행() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);


        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(backCategory);

        ExerciseJpaEntity benchpress1 = ExerciseJpaEntity.builder().name("벤치프레스").category(saveChestCategory).build();
        ExerciseJpaEntity flymachine1 = ExerciseJpaEntity.builder().name("플라이머신").category(saveChestCategory).build();
        ExerciseJpaEntity deadlift1 = ExerciseJpaEntity.builder().name("데드리프트").category(saveBackCategory).build();

        ExerciseJpaEntity exercise1 = exerciseJpaRepository.save(benchpress1);
        ExerciseJpaEntity exercise2 = exerciseJpaRepository.save(flymachine1);
        ExerciseJpaEntity exercise3 = exerciseJpaRepository.save(deadlift1);

        LocalDate saveDate = LocalDate.of(2024, 11, 25);
        DailyRecordJpaEntity dailyRecordJpaEntity = new DailyRecordJpaEntity(saveDate, saveUser);
        DailyRecordJpaEntity saveDailyRecordJpaEntity = dailyRecordJpaRepository.save(dailyRecordJpaEntity);
        RoutineJpaEntity saveRoutine = routineJpaRepository.save(new RoutineJpaEntity("name", saveUser));
        RoutineJpaEntity saveRoutine2 = routineJpaRepository.save(new RoutineJpaEntity("name", saveUser));

        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine, benchpress1));
        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine, benchpress1));

        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine2, benchpress1));
        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine2, benchpress1));

        // when
        System.out.println("=====Logic Start=====");

        exerciseService.removeExercises(benchpress1.getId());

        System.out.println("=====Logic End=====");
        // then
        List<ExerciseRoutineJpaEntity> allExerciseWithinRoutine = exerciseRoutineJpaRepository.findAllByRoutine(saveRoutine);
        List<ExerciseRoutineJpaEntity> allExerciseWithinRoutine2 = exerciseRoutineJpaRepository.findAllByRoutine(saveRoutine2);

        assertAll(() -> assertThat(allExerciseWithinRoutine.size()).isZero(),
                () -> assertThat(allExerciseWithinRoutine2.size()).isZero());
    }
}