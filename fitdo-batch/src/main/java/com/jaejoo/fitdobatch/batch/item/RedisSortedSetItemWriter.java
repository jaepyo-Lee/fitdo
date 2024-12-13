package com.jaejoo.fitdobatch.batch.item;

import com.jaejoo.fitdobatch.batch.mapping.UserScoreRow;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.ByteBuffer;

public class RedisSortedSetItemWriter implements ItemWriter<UserScoreRow> {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisSortedSetItemWriter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public static byte[] keySerialize(Long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        return buffer.array();
    }

    public static byte[] valueSerialize(Double value) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(value);
        return buffer.array();
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
