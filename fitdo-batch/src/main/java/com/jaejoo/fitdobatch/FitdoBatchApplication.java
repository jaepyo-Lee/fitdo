package com.jaejoo.fitdobatch;

import com.jaejoo.fitdobatch.batch.launcher.DailyScoreUpdateJobLauncher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@RequiredArgsConstructor
public class FitdoBatchApplication implements ApplicationRunner {
    private final DailyScoreUpdateJobLauncher jobLauncher;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        jobLauncher.launch();

    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(FitdoBatchApplication.class, args);
        final int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }

}
