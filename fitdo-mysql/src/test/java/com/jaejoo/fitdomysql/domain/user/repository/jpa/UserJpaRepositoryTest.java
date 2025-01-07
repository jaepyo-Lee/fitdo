package com.jaejoo.fitdomysql.domain.user.repository.jpa;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.core.User;
import com.jaejoo.fitdomysql.domain.user.repository.jpa.entity.UserJpaEntity;
import jakarta.persistence.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJpaRepositoryTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void saveAll() {
        // given
        long total = 0;
        List<UserJpaEntity> users = new ArrayList<>();
        for (int a = 0; a < 5; a++) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                users.add(new UserJpaEntity("" + i, AuthType.KAKAO, "user" + i, true, GrantRole.ROLE_USER));
            }
            userJpaRepository.saveAll(users);
            long end = System.currentTimeMillis();
            total += end - start;
        }

        System.out.println((double) (total) / 5000 + " 초 걸림");

        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }

    @Test
    void save() {
        // given
        long total = 0;
        for (int a = 0; a < 5; a++) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                userJpaRepository.save(new UserJpaEntity("" + i, AuthType.KAKAO, "user" + i, true, GrantRole.ROLE_USER));
            }
            long end = System.currentTimeMillis();
            total += end - start;
        }
        System.out.println((double) (total) / 5000 + " 초 걸림");
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Order(3)
    @Test
    void bulk_insert() throws SQLException {
        System.out.println(url);
        // given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            users.add(new UserJpaEntity("" + i, AuthType.KAKAO, "user" + i, true, GrantRole.ROLE_USER).toUserModel());
        }
        int total = 0;
        for (int a = 0; a < 5; a++) {
            long start = System.currentTimeMillis();
            Connection con = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO user_jpa_entity (auth_id,auth_type,height,new_flag,nickname,role,username,weight) VALUES(?,?,?,?,?,?,?,?) ";
            PreparedStatement pstmt = con.prepareStatement(sql);
            System.out.println(pstmt);

            con.setAutoCommit(false);
            // 100 단위로 끊기 위한 카운터 변수 선언
            int cnt = 0;

            try {
                for (User user : users) {
                    // ? 파라미터에 값 주입
                    pstmt.setString(7, user.getName());
                    pstmt.setString(5, user.getNickname());
                    pstmt.setString(1, user.getAccount().getAuthId());
                    pstmt.setBoolean(4, user.getAccount().isNewUser());
                    pstmt.setInt(3, user.getHeight());
                    pstmt.setInt(8, user.getWeight());
                    pstmt.setString(2, String.valueOf(user.getAccount().getAuthType()));
                    pstmt.setString(6, String.valueOf(user.getAccount().getRole()));

                    // 배치에 추가
                    pstmt.addBatch();

                    // batch 메모리에 넣은 후 파라미터 클리어
                    pstmt.clearParameters();

                    // 100개 단위로 배치 실행
                    if (cnt % 100 == 0 && cnt != 0) {
                        pstmt.executeBatch();
                        pstmt.clearBatch();
                    }
                }

                // 마지막으로 남은 배치 실행 및 커밋
                pstmt.executeBatch();
                con.commit();

            } catch (Exception e) {
                // 롤백
                con.rollback();
                throw e;
            } finally {
                // 자동 커밋 다시 설정
                con.setAutoCommit(true);
                con.close();
                // PreparedStatement 닫기
                pstmt.close();
            }
            long end = System.currentTimeMillis();
            total += end - start;
        }
        System.out.println((double) (total) / 5000 + " 초 걸림");
        // when
        System.out.println("=====Logic Start=====");


        System.out.println("=====Logic End=====");
        // then
    }
}