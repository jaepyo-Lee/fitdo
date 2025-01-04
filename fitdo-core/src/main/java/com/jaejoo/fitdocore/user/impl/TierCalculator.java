package com.jaejoo.fitdocore.user.impl;

import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdomysql.domain.user.core.Tier;
import com.jaejoo.fitdomysql.domain.user.core.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TierCalculator {
    private final RedisTemplate<String, String> redisTemplate;

    public FriendSimpleInfo calculate(User user) {
        long total = Long.parseLong(Objects.requireNonNull(redisTemplate.opsForValue().get("total")));
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        Long rank = zSet.rank("userScore", String.valueOf(user.getUserId()));
        if (rank == null) {
            rank = total; // 기본적으로 최하위로 설정
        }
        rank += 1;
        return new FriendSimpleInfo(user.getUserId(), user.getNickname(), Tier.calculateTier(total, rank).name());
    }
}
