package com.jaejoo.fitdo.domain.exercise.service.application;

import com.jaejoo.fitdo.domain.exercise.core.BodyPart;
import com.jaejoo.fitdo.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyExerciseRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.DailyRecordJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyExerciseRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.DailyRecordJpaEntity;
import com.jaejoo.fitdo.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
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

import static org.assertj.core.api.Assertions.assertThat;

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

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity chestCategory2 = CategoryJpaEntity.builder().user(saveUser2).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory2 = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(chestCategory);

        // when
        System.out.println("=====Logic Start=====");

        String name = "벤치프레스";
        String actual = exerciseService.createExercise(new ExerciseCreateCommand(saveChestCategory.getId(), name));

        System.out.println("=====Logic End=====");
        // then
        assertThat(actual).isEqualTo(name);
    }

    @Test
    void 운동목록조회() {
        // given
        UserJpaEntity user = UserJpaEntity.builder().build();
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity user2 = UserJpaEntity.builder().build();
        UserJpaEntity saveUser2 = userJpaRepository.save(user2);

        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity chestCategory2 = CategoryJpaEntity.builder().user(saveUser2).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory2 = categoryJpaRepository.save(chestCategory2);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.BACK).build();
        CategoryJpaEntity saveBackCategory = categoryJpaRepository.save(backCategory);

        ExerciseJpaEntity benchpress1 = ExerciseJpaEntity.builder().name("벤치프레스").category(saveChestCategory).build();
        ExerciseJpaEntity flymachine1 = ExerciseJpaEntity.builder().name("플라이머신").category(saveChestCategory).build();
        ExerciseJpaEntity deadlift1 = ExerciseJpaEntity.builder().name("데드리프트").category(saveBackCategory).build();

        ExerciseJpaEntity dumbellpress2 = ExerciseJpaEntity.builder().name("덤벨 프레스").category(saveChestCategory2).build();
        ExerciseJpaEntity pressmachine2 = ExerciseJpaEntity.builder().name("프레스 머신").category(saveChestCategory2).build();
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


        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.BACK).build();
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


        CategoryJpaEntity chestCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.CHEST).build();
        CategoryJpaEntity saveChestCategory = categoryJpaRepository.save(chestCategory);

        CategoryJpaEntity backCategory = CategoryJpaEntity.builder().user(saveUser).part(BodyPart.BACK).build();
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
}