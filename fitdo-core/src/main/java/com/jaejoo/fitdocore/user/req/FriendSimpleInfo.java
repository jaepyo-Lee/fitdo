package com.jaejoo.fitdocore.user.req;

import com.jaejoo.fitdomysql.domain.user.core.Tier;
import lombok.Getter;

@Getter
public class FriendSimpleInfo {
    private Long userId;
    private String nickname;
    private String tier;

    public FriendSimpleInfo(Long userId, String nickname, String tier) {
        this.userId = userId;
        this.nickname = nickname;
        this.tier = tier;
    }
    public static FriendSimpleInfo of(Long userId,String nickname,String tierString){
        return new FriendSimpleInfo(userId, nickname,tierString);
    }
}
