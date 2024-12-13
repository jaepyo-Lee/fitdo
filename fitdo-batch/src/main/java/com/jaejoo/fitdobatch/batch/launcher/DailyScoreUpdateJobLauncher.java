package com.jaejoo.fitdobatch.batch.launcher;

import com.jaejoo.fitdobatch.batch.job.ScoreJobConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DailyScoreUpdateJobLauncher {
    private final JobLauncher jobLauncher;
    private final ScoreJobConfiguration scoreJobConfiguration;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    public void launch() throws Exception {
        final JobParameters updateUserExerciseScoreJobParameter = new JobParametersBuilder()
                .addLocalDateTime("updateUserExerciseScore", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(scoreJobConfiguration.run(jobRepository,transactionManager),updateUserExerciseScoreJobParameter);
    }
}
