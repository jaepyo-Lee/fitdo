package com.jaejoo.fitdocore.user.service.application;


import com.jaejoo.fitdocore.user.FriendService;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.CategoryJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.ExerciseRoutineJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.RoutineJpaRepository;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.CategoryJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.ExerciseRoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.RoutineJpaEntity;
import com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity.enumerate.DeleteDelimiter;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConfiguration
@Transactional
class FriendServiceTest {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    FriendJpaRepository friendJpaRepository;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;
    @Autowired
    private ExerciseJpaRepository exerciseJpaRepository;
    @Autowired
    private RoutineJpaRepository routineJpaRepository;
    @Autowired
    private ExerciseRoutineJpaRepository exerciseRoutineJpaRepository;
    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @Test
    void 사용자의_친구목록_간단조회() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);
        redisTemplate.opsForValue().set("total", "2");
        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(saveUser, saveFriend, FriendStatus.APPLY));

        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        zSet.add("userScore", String.valueOf(saveFriend.getId()), 1);

        // when
        System.out.println("=====Logic Start=====");

        List<FriendSimpleInfo> friendSimpleInfos = friendService.readFriendsInfos(saveUser.getId());

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendSimpleInfos.size()).isEqualTo(1);
        zSet.removeRange("userScore", 0, -1);
    }

    @Test
    void 사용자의_친구정보_조회() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        CategoryJpaEntity category = CategoryJpaEntity.builder().part(BodyPart.BACK).build();
        CategoryJpaEntity saveCategory = categoryJpaRepository.save(category);

        ExerciseJpaEntity exercise = ExerciseJpaEntity.builder().user(saveUser).name("데드리프트").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).build();
        ExerciseJpaEntity saveExercise = exerciseJpaRepository.save(exercise);

        ExerciseJpaEntity exercise2 = ExerciseJpaEntity.builder().user(saveUser).name("데드리프트2").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).build();
        ExerciseJpaEntity saveExercise2 = exerciseJpaRepository.save(exercise2);

        ExerciseJpaEntity exercise3 = ExerciseJpaEntity.builder().user(saveUser).name("데드리프트3").deleteDelimiter(DeleteDelimiter.IN_USER).category(saveCategory).build();
        ExerciseJpaEntity saveExercise3 = exerciseJpaRepository.save(exercise3);

        RoutineJpaEntity routine = RoutineJpaEntity.builder()
                .name("routine1")
                .user(saveUser)
                .build();
        RoutineJpaEntity saveRoutine = routineJpaRepository.save(routine);

        ExerciseRoutineJpaEntity exerciseRoutine = ExerciseRoutineJpaEntity.builder()
                .routine(saveRoutine)
                .exercise(saveExercise)
                .build();
        ExerciseRoutineJpaEntity exerciseRoutine2 = ExerciseRoutineJpaEntity.builder()
                .routine(saveRoutine)
                .exercise(saveExercise2)
                .build();
        ExerciseRoutineJpaEntity exerciseRoutine3 = ExerciseRoutineJpaEntity.builder()
                .routine(saveRoutine)
                .exercise(saveExercise3)
                .build();
        ExerciseRoutineJpaEntity saveExerciseRoutine = exerciseRoutineJpaRepository.save(exerciseRoutine);
        ExerciseRoutineJpaEntity saveExerciseRoutine2 = exerciseRoutineJpaRepository.save(exerciseRoutine2);
        ExerciseRoutineJpaEntity saveExerciseRoutine3 = exerciseRoutineJpaRepository.save(exerciseRoutine3);

        // when
        System.out.println("=====Logic Start=====");

        FriendDetailInfo friendDetailInfo = friendService.readFriendDetailInfo(saveUser.getId());

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(friendDetailInfo.getRoutines().size()).isEqualTo(1),
                () -> assertThat(friendDetailInfo.getUserId()).isEqualTo(saveUser.getId()),
                () -> assertThat(friendDetailInfo.getRoutines().get(0).getExercises().size()).isEqualTo(3));
    }
}