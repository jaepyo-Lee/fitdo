package com.jaejoo.fitdobatch.runner;

import com.jaejoo.fitdobatch.launcher.ScoreJobLauncher;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@RequiredArgsConstructor
public class BatchRunner implements ApplicationRunner {
    private final ScoreJobLauncher scoreJobLauncher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scoreJobLauncher.launch();
    }
}
