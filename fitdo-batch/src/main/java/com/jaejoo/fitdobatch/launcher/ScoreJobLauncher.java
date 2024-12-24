package com.jaejoo.fitdobatch.launcher;

import com.jaejoo.fitdobatch.job.ScoreJobConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class ScoreJobLauncher {
    private final JobLauncher jobLauncher;
    private final ScoreJobConfiguration scoreJobConfiguration;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    public void launch() throws Exception {
        JobParameters updateScoreDate = new JobParametersBuilder()
                .addLocalDateTime("updateScoreDate", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(scoreJobConfiguration.start(jobRepository, transactionManager),updateScoreDate); ;
    }
}
