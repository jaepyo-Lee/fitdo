package com.jaejoo.fitdocore.exercise.service.application;

import com.jaejoo.fitdocore.exercise.ExerciseService;
import com.jaejoo.fitdocore.exercise.req.ExerciseCreateCommand;
import com.jaejoo.fitdocore.exercise.res.ExercisesWithinCategory;
import com.jaejoo.fitdocore.exercise.res.FindExercisesWithCategory;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.RecordQueryRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.*;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private DailyJpaRepository dailyJpaRepository;
    @Autowired
    private DailyExerciseJpaRepository dailyExerciseJpaRepository;
    @Autowired
    private RecordQueryRepository recordQueryRepository;
    @Autowired
    private RoutineJpaRepository routineJpaRepository;
    @Autowired
    private ExerciseRoutineJpaRepository exerciseRoutineJpaRepository;
    @Autowired
    private ExerciseSetJpaRepository exerciseSetJpaRepository;

    @Test
    void 운동목록생성() {
        UserJpaEntity saveUser = saveUser();
        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);

        String name = "벤치프레스";
        exerciseService.createExercise(new ExerciseCreateCommand(saveChestCategory.getId(), name, saveUser.getId()));

        List<FindExercisesWithCategory> exercisesWithCategoryOf = exerciseService.findExercisesWithCategoryFor(saveUser.getId());

        assertThat(exercisesWithCategoryOf.size()).isEqualTo(1);
    }

    @Test
    void 모든부위의_운동목록_조회() {
        UserJpaEntity saveUser = saveUser();
        UserJpaEntity saveUser2 = saveUser();

        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);
        CategoryJpaEntity saveBackCategory = saveCategory(BodyPart.BACK);

        saveExercise("벤치프레스", saveChestCategory, saveUser);
        saveExercise("플라이머신", saveChestCategory, saveUser);
        saveExercise("데드리프트", saveBackCategory, saveUser);

        saveExercise("덤벨 프레스", saveChestCategory, saveUser2);
        saveExercise("프레스 머신", saveChestCategory, saveUser2);

        List<FindExercisesWithCategory> exercisesWithCategoryOf = exerciseService.findExercisesWithCategoryFor(saveUser.getId());

        assertThat(exercisesWithCategoryOf.size()).isEqualTo(2);
    }

    @Test
    void 운동삭제해도_완전삭제되지않음() {
        UserJpaEntity saveUser = saveUser();

        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);
        CategoryJpaEntity saveBackCategory = saveCategory(BodyPart.BACK);

        ExerciseJpaEntity exercise1 = saveExercise("벤치프레스", saveChestCategory, saveUser);
        saveExercise("플라이머신", saveChestCategory, saveUser);
        saveExercise("데드리프트", saveBackCategory, saveUser);

        exerciseService.removeExercises(exercise1.getId());

        List<ExerciseJpaEntity> all = exerciseJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    void 운동삭제시_기록된_운동은_삭제되지않도록_soft_delete진행() {
        UserJpaEntity saveUser = saveUser();

        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);
        ExerciseJpaEntity benchpress1 = saveExercise("벤치프레스", saveChestCategory, saveUser);

        LocalDate saveDate = LocalDate.of(2024, 11, 25);
        DailyJpaEntity saveDailyJpaEntity = saveDaily(saveDate, saveUser);
        DailyExerciseJpaEntity dailyExercise = saveDailyExercise(saveDailyJpaEntity, benchpress1);

        exerciseSetJpaRepository.save(ExerciseSetJpaEntity.builder().number(1).volume(10).done(false).weight(50).dailyExercise(dailyExercise).build());

        exerciseService.removeExercises(benchpress1.getId());

        List<DailyExerciseJpaEntity> exerciseRecords = recordQueryRepository.findExerciseRecordsInDailyRecordDividedBy(benchpress1, saveDailyJpaEntity);
        assertThat(exerciseRecords.size()).isOne();
    }

    @Test
    void 운동삭제후_조회시_삭제된것은조회되면안됌() {
        UserJpaEntity saveUser = saveUser();

        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);
        CategoryJpaEntity saveBackCategory = saveCategory(BodyPart.BACK);

        ExerciseJpaEntity exercise1 = saveExercise("벤치프레스", saveChestCategory, saveUser);
        ExerciseJpaEntity exercise2 = saveExercise("플라이머신", saveChestCategory, saveUser);
        ExerciseJpaEntity exercise3 = saveExercise("데드리프트", saveBackCategory, saveUser);

        LocalDate saveDate = LocalDate.of(2024, 11, 25);
        DailyJpaEntity saveDailyJpaEntity = saveDaily(saveDate, saveUser);
        saveDailyExercise(saveDailyJpaEntity, exercise1);

        exerciseService.removeExercises(exercise1.getId());

        List<FindExercisesWithCategory> real = exerciseService.findExercisesWithCategoryFor(saveUser.getId());

        assertThat(real).usingRecursiveComparison().isEqualTo(getExpectedExerciseList(exercise2, exercise3, saveChestCategory, saveBackCategory));
    }

    @Test
    void 운동삭제시_삭제된운동이_포함하고있는_루틴에는_운동이_지워지면안됌() {
        UserJpaEntity saveUser = saveUser();

        CategoryJpaEntity saveChestCategory = saveCategory(BodyPart.CHEST);
        CategoryJpaEntity saveBackCategory = saveCategory(BodyPart.BACK);

        ExerciseJpaEntity exercise1 = saveExercise("벤치프레스", saveChestCategory, saveUser);
        ExerciseJpaEntity exercise2 = saveExercise("플라이머신", saveChestCategory, saveUser);
        ExerciseJpaEntity exercise3 = saveExercise("데드리프트", saveBackCategory, saveUser);

        RoutineJpaEntity saveRoutine1 = routineJpaRepository.save(new RoutineJpaEntity("name1", saveUser));
        RoutineJpaEntity saveRoutine2 = routineJpaRepository.save(new RoutineJpaEntity("name2", saveUser));

        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine1, exercise1));
        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine1, exercise2));
        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine2, exercise1));
        exerciseRoutineJpaRepository.save(new ExerciseRoutineJpaEntity(saveRoutine2, exercise3));

        exerciseService.removeExercises(exercise1.getId());

        List<ExerciseRoutineJpaEntity> allExercisesRoutine1 = exerciseRoutineJpaRepository.findAllByRoutine(saveRoutine1);
        List<ExerciseRoutineJpaEntity> allExercisesRoutine2 = exerciseRoutineJpaRepository.findAllByRoutine(saveRoutine2);

        assertAll(() -> assertThat(allExercisesRoutine1.size()).isEqualTo(2),
                () -> assertThat(allExercisesRoutine2.size()).isEqualTo(2));
    }

    private UserJpaEntity saveUser() {
        return userJpaRepository.save(UserJpaEntity.builder().build());
    }

    private CategoryJpaEntity saveCategory(BodyPart bodyPart) {
        return categoryJpaRepository.save(CategoryJpaEntity.builder().part(bodyPart).build());
    }

    private ExerciseJpaEntity saveExercise(String name, CategoryJpaEntity category, UserJpaEntity user) {
        return exerciseJpaRepository.save(ExerciseJpaEntity.builder()
                .user(user)
                .name(name)
                .category(category)
                .deleteDelimiter(DeleteDelimiter.IN_USER)
                .build());
    }

    private DailyJpaEntity saveDaily(LocalDate date, UserJpaEntity user) {
        return dailyJpaRepository.save(new DailyJpaEntity(date, user));
    }

    private DailyExerciseJpaEntity saveDailyExercise(DailyJpaEntity daily, ExerciseJpaEntity exercise) {
        return dailyExerciseJpaRepository.save(DailyExerciseJpaEntity.builder()
                .exercise(exercise)
                .daily(daily)
                .build());
    }

    private List<FindExercisesWithCategory> getExpectedExerciseList(ExerciseJpaEntity exercise2, ExerciseJpaEntity exercise3, CategoryJpaEntity chestCategory, CategoryJpaEntity backCategory) {
        return List.of(
                createFindExercisesWithCategory(chestCategory, createExercisesWithinCategory(exercise2)),
                createFindExercisesWithCategory(backCategory, createExercisesWithinCategory(exercise3))
        );
    }

    private FindExercisesWithCategory createFindExercisesWithCategory(CategoryJpaEntity category, ExercisesWithinCategory exercise) {
        return FindExercisesWithCategory.builder()
                .categoryId(category.getId())
                .categoryName(category.getPartName())
                .exercises(List.of(exercise))
                .build();
    }

    private ExercisesWithinCategory createExercisesWithinCategory(ExerciseJpaEntity exercise) {
        return ExercisesWithinCategory.builder()
                .exerciseId(exercise.getId())
                .exerciseName(exercise.getName())
                .build();
    }
}
