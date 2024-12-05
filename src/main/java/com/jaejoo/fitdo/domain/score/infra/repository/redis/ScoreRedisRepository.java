package com.jaejoo.fitdo.domain.score.infra.repository.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ScoreRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOperations;

    @PostConstruct
    private void init() {
        zSetOperations = redisTemplate.opsForZSet();
    }

    public void add(String userId, Double score) {
        zSetOperations.add("score", userId, score);
    }
}
