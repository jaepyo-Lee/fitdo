package com.jaejoo.fitdo.domain.user.infra.repository.jpa.entity;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;
import com.jaejoo.fitdo.domain.user.core.GrantRole;
import com.jaejoo.fitdo.domain.user.core.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    private String username;
    private String authId;
    private boolean newFlag;
    private int height;
    private int weight;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    @Enumerated(EnumType.STRING)
    private GrantRole role;

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
        return new UserJpaEntity(account.getUserId(), user.getName(), account.getAuthId(), account.isNewUser(), user.getHeight(), user.getWeight(), account.getAuthType(), account.getRole());
    }

    public UserJpaEntity(Long id, String username, String authId, boolean newFlag, int height, int weight, AuthType authType, GrantRole role) {
        this.id = id;
        this.username = username;
        this.authId = authId;
        this.newFlag = newFlag;
        this.height = height;
        this.weight = weight;
        this.authType = authType;
        this.role = role;
    }

    @Builder
    public UserJpaEntity(String username, String authId, boolean newFlag, int height, int weight, AuthType authType, GrantRole role) {
        this.username = username;
        this.authId = authId;
        this.newFlag = newFlag;
        this.height = height;
        this.weight = weight;
        this.authType = authType;
        this.role = role;
    }

    public Account toAccountModel() {
        return new Account(authId, newFlag, id, role, authType);
    }

    public User toUserModel() {
        return new User(new Account(authId, newFlag, id, role, authType), height, weight);
    }
}
