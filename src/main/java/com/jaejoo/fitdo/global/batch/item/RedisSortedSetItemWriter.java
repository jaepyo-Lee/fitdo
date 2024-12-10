package com.jaejoo.fitdo.global.batch.item;

import com.jaejoo.fitdo.global.batch.mapping.UserScoreRow;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSortedSetItemWriter implements ItemWriter<UserScoreRow> {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisSortedSetItemWriter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Chunk<? extends UserScoreRow> chunk) throws Exception {
        for (UserScoreRow userScoreRow : chunk) {
            redisTemplate.opsForZSet().add("userScores", String.valueOf(userScoreRow.getUserId()), userScoreRow.getScore());
        }
    }
}
