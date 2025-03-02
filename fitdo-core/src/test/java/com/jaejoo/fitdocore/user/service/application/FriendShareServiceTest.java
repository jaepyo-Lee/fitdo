package com.jaejoo.fitdocore.user.service.application;

import com.jaejoo.fitdocore.user.FriendShareService;
import com.jaejoo.fitdocore.user.req.FriendApplyCommand;
import com.jaejoo.fitdocore.util.AESConverter;
import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.FriendJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.UserJpaRepository;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendJpaEntity;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.FriendStatus;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class FriendShareServiceTest {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    FriendShareService friendService;
    @Autowired
    FriendJpaRepository friendJpaRepository;
    @MockBean
    private AESConverter aesConverter;
    @Autowired
    private Environment env;

    @Test
    void 친구추가시_한쪽만_친구신청상태로_등록() throws Exception {
        // given
        LinkedList<Object> objects = new LinkedList<>();
        ArrayList<Object> list = new ArrayList<>();
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity sender = userJpaRepository.save(user);

        UserJpaEntity friend = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity receiver = userJpaRepository.save(friend);
        String serialize = "testSerialize";

        when(aesConverter.deserialize(serialize)).thenReturn(String.valueOf(user.getId()));

        // when
        System.out.println("=====Logic Start=====");

        friendService.applyFriend(new FriendApplyCommand(receiver.getId(), serialize)); //암호화되어있어야함, 근데 지금은 아니어서 안됌

        System.out.println("=====Logic End=====");
        // then
        assertThat(friendJpaRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("본인을 친구추가할 경우")
    @Test
    void saneUserApplyFriendExceptionTest() throws Exception {
        LinkedList<Object> objects = new LinkedList<>();
        ArrayList<Object> list = new ArrayList<>();
        UserJpaEntity user = UserJpaEntity.from("authId", AuthType.KAKAO, "name", true, GrantRole.ROLE_ADMIN);
        UserJpaEntity sender = userJpaRepository.save(user);

        String serialize = "testSerialize";

        when(aesConverter.deserialize(serialize)).thenReturn(String.valueOf(user.getId()));

        // when
        System.out.println("=====Logic Start=====");

        assertThrows(IllegalArgumentException.class,() -> friendService.applyFriend(new FriendApplyCommand(sender.getId(), serialize)));

        System.out.println("=====Logic End=====");
        // then
    }

    @DisplayName("딥링크 생성 테스트")
    @Test
    void generateDeeplinkTest() throws Exception {
        // given
        long userId = 1L;
        String testprefix = "testSerialize";
        when(aesConverter.serialize(String.valueOf(userId))).thenReturn(testprefix);
        String property = env.getProperty("deeplink.url");
        // when
        String deeplink = friendService.generateDeepLink(userId);
        // then
        Assertions.assertThat(deeplink).isEqualTo(property+testprefix);
    }
}