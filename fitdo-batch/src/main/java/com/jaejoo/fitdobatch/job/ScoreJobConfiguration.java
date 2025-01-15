package com.jaejoo.fitdobatch.job;

import com.jaejoo.fitdobatch.item.RedisSortedSetItemWriter;
import com.jaejoo.fitdobatch.mapping.CalculateScoreRow;
import com.jaejoo.fitdobatch.mapping.UserScoreRow;
import com.jaejoo.fitdobatch.mapping.rowmapper.CalculateScoreRowMapper;
import com.jaejoo.fitdobatch.mapping.rowmapper.UserScoreMapper;
import com.jaejoo.fitdomysql.domain.exercise.core.BodyPart;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.redis.RedisItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ScoreJobConfiguration {
    private final DataSource dataSource;
    private static final int CHUNK_SIZE = 100;
    private final RedisTemplate<String, String> redisTemplate;

    @Bean
    public Job start(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        System.out.println("job");
        return new JobBuilder("job", jobRepository)
                .start(calculateScoreStep(jobRepository, transactionManager)) //점수 계산 및 DB주입
                .next(loadScoreDataToRedis(jobRepository, transactionManager))//redis 갱신
                .build();
    }

    public Step loadScoreDataToRedis(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("loadScoreDataToRedis", jobRepository)
                .<UserScoreRow, UserScoreRow>chunk(100, transactionManager)
                .reader(userScoreReader())
                .processor(scoreProcessor())
                .writer(cachingUpdateScore())
                .build();
    }

    public ItemWriter<UserScoreRow> cachingUpdateScore() {
        return new RedisSortedSetItemWriter(redisTemplate);
    }

    public ItemProcessor<UserScoreRow, UserScoreRow> scoreProcessor() {
        return request -> {
            return request;
        };
    }

    public JdbcPagingItemReader<UserScoreRow> userScoreReader() throws Exception {
        JdbcPagingItemReader<UserScoreRow> itemReader = new JdbcPagingItemReaderBuilder<UserScoreRow>()
                .dataSource(dataSource)
                .fetchSize(CHUNK_SIZE)
                .pageSize(CHUNK_SIZE)
                .rowMapper(new UserScoreMapper())
                .queryProvider(createScoreQueryProvider())
                .name("scoreJdbcItemReader")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }

    public PagingQueryProvider createScoreQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);

        // SELECT 절
        queryProvider.setSelectClause("""
                    SELECT user_id as userId, score as score
                """);

        // FROM 절
        queryProvider.setFromClause("""
                    FROM score_jpa_entity
                """);

        queryProvider.setSortKey("userId");

        return queryProvider.getObject();
    }

    public Step calculateScoreStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("calculateScore", jobRepository)
                .<CalculateScoreRow, UserScoreRow>chunk(100, transactionManager)
                .reader(readUserExerciseRecord())
                .processor(calculateScoreProcessor())
                .writer(updateUserScore())
                .build();
    }

    private JdbcPagingItemReader<CalculateScoreRow> readUserExerciseRecord() throws Exception {
        HashMap<String, Object> whereParam = new HashMap<>();
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        whereParam.put("date", yesterday);  // 수정된 부분
        JdbcPagingItemReader<CalculateScoreRow> itemReader = new JdbcPagingItemReaderBuilder<CalculateScoreRow>()
                .dataSource(dataSource)
                .fetchSize(CHUNK_SIZE)
                .pageSize(CHUNK_SIZE)
                .queryProvider(createQueryProvider())
                .rowMapper(new CalculateScoreRowMapper())
                .parameterValues(whereParam)
                .name("scoreElementJdbcItemReader")
                .build();
        itemReader.afterPropertiesSet();
        return itemReader;
    }

    private PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);

        // SELECT 절
        queryProvider.setSelectClause("""
                        user_jpa_entity.id as userId, 
                        user_jpa_entity.weight AS userWeight, 
                        user_jpa_entity.height AS userHeight,
                        der.weight AS recordWeight, 
                        der.volume AS recordVolume,
                        der.is_progress AS isProgress, 
                        c.part AS bodyPart
                """);

        // FROM 절
        queryProvider.setFromClause("""
                        daily_exercise_record_jpa_entity AS der
                    JOIN 
                        daily_record_jpa_entity AS dr ON dr.id = der.daily_record_id
                    JOIN 
                        user_jpa_entity  ON dr.user_id = user_jpa_entity.id
                    JOIN 
                        exercise_jpa_entity AS e ON der.exercise_id = e.id
                    JOIN 
                        category_jpa_entity AS c ON e.category_id = c.id
                """);

        // WHERE 절
        queryProvider.setWhereClause("WHERE dr.exercise_date = :date");
        queryProvider.setSortKey("userId");

        return queryProvider.getObject();
    }


    public ItemProcessor<CalculateScoreRow, UserScoreRow> calculateScoreProcessor() {
        Map<BodyPart, Double> bodyPartStrength = Map.of(
                BodyPart.CHEST, 1.0,
                BodyPart.LEG, 2.0,
                BodyPart.ARM, 0.7,
                BodyPart.SHOULDER, 0.8,
                BodyPart.BACK, 1.3,
                BodyPart.ABS, 0.6,
                BodyPart.HIP, 1.8,
                BodyPart.FULL_BODY, 2.5
        );

        Map<BodyPart, Double> bodyPartWeighting = bodyPartStrength.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> 1 / entry.getValue()
                ));
        return request -> {
            BodyPart bodyPart = request.getBodyPart();
            Integer reps = request.getRecordVolume();
            Integer exerciseWeight = request.getRecordWeight();
            Double weightingFactor = bodyPartWeighting.getOrDefault(bodyPart, 1.0);
            Double score = exerciseWeight * reps * weightingFactor;
            Double finalScore = score * 0.00000001;

            return new UserScoreRow(request.getUserId(), finalScore);  // UserScoreRow 객체 반환
        };
    }


    public ItemWriter<UserScoreRow> updateUserScore() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return items -> {
            for (UserScoreRow item : items) {
                // user_id를 기준으로 업데이트
                int updated = jdbcTemplate.update(
                        "UPDATE score_jpa_entity SET score = score + ? WHERE user_id = ?",
                        item.getScore(), item.getUserId());

                // 업데이트되지 않았으면 삽입
                if (updated == 0) {
                    jdbcTemplate.update(
                            "INSERT INTO score_jpa_entity (user_id, score) VALUES (?, ?)",
                            item.getUserId(), item.getScore());
                }
            }
        };
    }
}
