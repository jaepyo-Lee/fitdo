package com.jaejoo.fitdobatch.item;

import com.jaejoo.fitdobatch.mapping.UserScoreRow;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSortedSetItemWriter implements ItemWriter<UserScoreRow> {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisSortedSetItemWriter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Chunk<? extends UserScoreRow> chunk) throws Exception {
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            for (UserScoreRow item : chunk.getItems()) {
                redisConnection.zAdd("userScores".getBytes(), item.getScore(),
                        String.valueOf(item.getUserId()).getBytes());
            }
            return null;
        });
    }
}
