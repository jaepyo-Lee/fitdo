package com.jaejoo.fitdomysql.domain.user.repository;


import com.jaejoo.fitdomysql.domain.auth.enumerate.AuthType;
import com.jaejoo.fitdomysql.domain.user.core.Account;

public interface AccountRepository {
    Account findOrSaveByAuthId(String authId, AuthType platformType, String username);

    Account findByUserId(Long userId);
}
