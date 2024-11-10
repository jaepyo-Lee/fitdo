package com.jaejoo.fitdo.domain.user.infra.repository;

import com.jaejoo.fitdo.domain.auth.service.application.req.AuthType;
import com.jaejoo.fitdo.domain.user.core.Account;

public interface AccountRepository {
    Account findOrSaveByAuthId(String authId, AuthType platformType, String username);
}
