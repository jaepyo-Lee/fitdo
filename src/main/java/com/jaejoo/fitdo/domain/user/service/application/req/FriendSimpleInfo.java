package com.jaejoo.fitdo.domain.user.service.application.req;

import com.jaejoo.fitdo.domain.user.core.Tier;
import lombok.Getter;

@Getter
public class FriendSimpleInfo {
    private Long userId;
    private String nickname;
    private Tier tier;

    public FriendSimpleInfo(Long userId, String nickname, Tier tier) {
        this.userId = userId;
        this.nickname = nickname;
        this.tier = tier;
    }
}
