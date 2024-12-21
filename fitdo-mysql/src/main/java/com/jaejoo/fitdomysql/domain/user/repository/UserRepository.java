package com.jaejoo.fitdomysql.domain.user.repository;

import com.jaejoo.fitdomysql.domain.user.core.User;

public interface UserRepository {
    User findById(Long id);

    User save(User user);

    boolean isExistNickName(String s);
    Integer findAllSize();
}
