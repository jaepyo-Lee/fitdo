package com.jaejoo.fitdobatch.item;

import com.jaejoo.fitdobatch.mapping.UserScoreRow;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisSortedSetItemWriter implements ItemWriter<UserScoreRow> {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisSortedSetItemWriter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Chunk<? extends UserScoreRow> chunk) throws Exception {
        long start = System.currentTimeMillis();
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            StringRedisConnection stringRedisConnection = (StringRedisConnection) redisConnection;
            for (UserScoreRow userScoreRow : chunk) {
                stringRedisConnection.zAdd("userScores", userScoreRow.getScore(), String.valueOf(userScoreRow.getUserId()));
            }
            redisConnection.closePipeline();
            return null;
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
