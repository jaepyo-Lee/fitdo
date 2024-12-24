package com.jaejoo.fitdocore.user.service.application;


import com.jaejoo.fitdocore.user.FriendService;
import com.jaejoo.fitdocore.user.req.FriendApplyCommand;
import com.jaejoo.fitdocore.user.req.FriendApplyConfirmCommand;
import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdocore.user.req.ReadApplierInfo;
import com.jaejoo.fitdocore.user.res.FriendDetailInfo;
import com.jaejoo.fitdocore.util.AESConverter;
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
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FriendServiceTest {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    EntityManager em;
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
    private AESConverter converter;
    @Test
    void 친구추가시_한쪽만_친구신청상태로_등록() throws Exception {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);
        String serialize = converter.serialize(String.valueOf(saveUser.getId()));

        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(saveFriend.getId(), serialize)); //암호화되어있어야함, 근데 지금은 아니어서 안됌

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendJpaRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void 친구신청시_상태는_친구신청을_보낸사람이_FROM이되어_APPLY상태이어야한다_추가받은사람은_아무엔티티도없다() throws Exception {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);
        String serialize = converter.serialize(String.valueOf(saveUser.getId()));
        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(saveFriend.getId(), serialize)); //암호화되어있어야함, 근데 지금은 아니어서 안됌

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        int cnt = 0;
        for (FriendJpaEntity friendJpaEntity : all) {
            if (friendJpaEntity.isSupport(FriendStatus.APPLY)) {
                cnt++;
            }
        }
        int finalCnt = cnt;
        assertAll(() -> assertThat(finalCnt).isEqualTo(2),
                () -> assertThat(all.size()).isEqualTo(2));
    }

    @Test
    void 친구신청수락시_요청과응답사용자_모두_친구로_등록되고ACCEPT됩니다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);

        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(saveUser, saveFriend, FriendStatus.APPLY));

        // when
        System.out.println("=====Logic Start=====");

        friendService.manageFriendApply(new FriendApplyConfirmCommand(saveUser.getId(), saveFriend.getId(), true));

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        int cnt = (int) all.stream().filter(friendJpaEntity -> friendJpaEntity.isSupport(FriendStatus.ACCEPT)).count();
        assertAll(() -> assertThat(all.size()).isEqualTo(2),
                () -> assertThat(cnt).isEqualTo(2));
    }

    @Test
    void 친구신청거절시_목록이_hard_delete됩니다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "sender", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity sender = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "receiver", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity receiver = userJpaRepository.save(friend);

        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(sender, receiver, FriendStatus.APPLY));

        // when
        System.out.println("=====Logic Start=====");
        System.out.println(sender.getId());
        System.out.println(receiver.getId());
        friendService.manageFriendApply(new FriendApplyConfirmCommand(sender.getId(), receiver.getId(), false));
//        friendService.manageFriendApply(new FriendApplyConfirmCommand( saveFriend.getId(),saveUser.getId(), false));

        System.out.println("=====Logic End=====");
        // then
        List<FriendJpaEntity> all = friendJpaRepository.findAll();
        assertAll(() -> assertThat(all.size()).isEqualTo(0));
    }


    @Test
    void 친구신청목록조회시_신청을받은사람만_조회된다() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);

        FriendJpaEntity saveFriendJpaEntity = friendJpaRepository.save(new FriendJpaEntity(saveUser, saveFriend, FriendStatus.APPLY));

        // when
        System.out.println("=====Logic Start=====");

        List<ReadApplierInfo> receiverApplies = friendService.readFriendApplies(saveFriend.getId());
        List<ReadApplierInfo> senderApplies = friendService.readFriendApplies(saveUser.getId());

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(receiverApplies.size()).isEqualTo(1),
                () -> assertThat(senderApplies.size()).isEqualTo(0));
    }

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void 사용자의_친구목록_간단조회() {
        // given
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveUser = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity saveFriend = userJpaRepository.save(friend);

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

        RoutineJpaEntity routine = RoutineJpaEntity.builder()
                .name("routine1")
                .user(saveUser)
                .build();
        RoutineJpaEntity saveRoutine = routineJpaRepository.save(routine);

        ExerciseRoutineJpaEntity exerciseRoutine = ExerciseRoutineJpaEntity.builder()
                .routine(saveRoutine)
                .exercise(saveExercise)
                .build();
        ExerciseRoutineJpaEntity saveExerciseRoutine = exerciseRoutineJpaRepository.save(exerciseRoutine);

        // when
        System.out.println("=====Logic Start=====");

        FriendDetailInfo friendDetailInfo = friendService.readFriendDetailInfo(saveUser.getId());

        System.out.println("=====Logic End=====");
        // then
        assertAll(() -> assertThat(friendDetailInfo.getRoutines().size()).isEqualTo(1),
                () -> assertThat(friendDetailInfo.getUserId()).isEqualTo(saveUser.getId()),
                () -> assertThat(friendDetailInfo.getRoutines().get(0).getExercises().size()).isEqualTo(1));
    }
}