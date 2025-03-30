package com.jaejoo.fitdomysql.domain.user.repository.jpa.entity;

import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;
import com.jaejoo.fitdomysql.domain.user.core.GrantRole;
import com.jaejoo.fitdomysql.domain.user.core.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    private String username;
    private String nickname;
    private String authId;
    private boolean newFlag;
    private int height;
    private int weight;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    @Enumerated(EnumType.STRING)
    private GrantRole role;
    @Builder.Default
    private Boolean active=Boolean.TRUE;

    public UserJpaEntity(String authId, AuthType platformType, String username, Boolean newFlag, GrantRole role) {
        this.authId = authId;
        this.username = username;
        this.newFlag = newFlag;
        this.authType = platformType;
        this.role = role;
    }

    public static UserJpaEntity from(String authId, AuthType platformType, String username, Boolean newFlag, GrantRole role) {
        return new UserJpaEntity(authId, platformType, username, newFlag, role);
    }

    public static UserJpaEntity from(User user) {
        Account account = user.getAccount();
        return new UserJpaEntity(account.getUserId(), user.getName(), account.getAuthId(), account.isNewUser(), user.getHeight(), user.getWeight(), account.getAuthType(), account.getRole(), user.getNickname(),account.getActive());
    }

    public UserJpaEntity(Long id, String username, String authId, boolean newFlag, int height, int weight, AuthType authType, GrantRole role, String nickname,Boolean active) {
        this.id = id;
        this.username = username;
        this.authId = authId;
        this.newFlag = newFlag;
        this.height = height;
        this.weight = weight;
        this.authType = authType;
        this.role = role;
        this.nickname = nickname;
        this.active = active;
    }

    public Account toAccountModel() {
        return new Account(authId, newFlag, id, role, authType, active);
    }

    public User toUserModel() {
        return new User(new Account(authId, newFlag, id, role, authType, active), height, weight, nickname);
    }
}
