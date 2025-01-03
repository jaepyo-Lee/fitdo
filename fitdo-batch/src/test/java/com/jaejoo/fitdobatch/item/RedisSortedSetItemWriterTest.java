package com.jaejoo.fitdobatch.item;

import com.jaejoo.fitdobatch.mapping.UserScoreRow;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RedisSortedSetItemWriterTest {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void redis파이프라이닝_테스트() throws Exception {
        // given
        RedisSortedSetItemWriter redisSortedSetItemWriter = new RedisSortedSetItemWriter(redisTemplate);

        // when
        System.out.println("=====Logic Start=====");
        long total = 0;
        for(int a=0;a<10;a++){
            long start = System.currentTimeMillis();
            Chunk<UserScoreRow> chunk = new Chunk<>();
            for (int i = 1; i <= 10; i++) {
                chunk.add(new UserScoreRow((long) i, (double) i));
            }
            redisSortedSetItemWriter.write(chunk);
            long end = System.currentTimeMillis();
            total += end - start;
            Thread.sleep(500);
        }
        System.out.println(total/10);

        System.out.println("=====Logic End=====");
        // then

    }
}