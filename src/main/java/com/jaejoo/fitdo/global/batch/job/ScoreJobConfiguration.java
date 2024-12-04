package com.jaejoo.fitdo.global.batch.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScoreJobConfiguration {
    /*private final DataSource dataSource;
    private static final int CHUNK_SIZE = 100;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new JobBuilder("job", jobRepository)
                .start(calculateScoreStep(jobRepository, transactionManager))//연속 출석일수 계산
                .next()//오늘의 사용자 점수 생성
                .next(step2(jobRepository, transactionManager))//redis에 쓰기
                .next(step3(jobRepository, transactionManager))//db에 쓰기
                .build();
    }

    @Bean
    public Step calculateScoreStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
        return new StepBuilder("calculateScore", jobRepository)
                .<>chunk(100, transactionManager)
                .reader(readUserExerciseRecord())
                .processor()
                .writer()
                .build();
    }

    private JdbcPagingItemReader<?> readUserExerciseRecord() throws Exception {
        HashMap<String, Object> whereParam = new HashMap<>();
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        whereParam.put("yesterday", yesterday);
        new JdbcPagingItemReaderBuilder<>()
                .dataSource(dataSource)
                .fetchSize(CHUNK_SIZE)
                .pageSize(CHUNK_SIZE)
                .queryProvider(createQueryProvider())
                .parameterValues(whereParam)
                .rowMapper(new BeanPropertyRowMapper<>(User.class))


    }

    private PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("select u.id,");
        queryProvider.setFromClause("from daily_exercise_record_jpa_entity as der " +
                "join daily_record_jpa_entity as dr on der.daily_record_id=dr.id " +
                "join user_jpa_entity as u on dr.user_id=u.id");
        queryProvider.setWhereClause("dr.date=:yesterday");
        return queryProvider.getObject();
    }

    *//**
     select * from daily_exercise_record_jpa_entity as der
     join daily_record_jpa_entity as dr on dr.id=der.daily_record_id
     join user_jpa_entity as u on dr.user_id=u.id
     where dr.date='2024-12-04';
     */
}
