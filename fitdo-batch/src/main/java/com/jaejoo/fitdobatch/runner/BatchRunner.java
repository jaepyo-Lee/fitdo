package com.jaejoo.fitdobatch.runner;

import com.jaejoo.fitdobatch.launcher.ScoreJobLauncher;
import com.jaejoo.fitdobatch.notification.DiscordBatchNotifier;
import com.jaejoo.fitdobatch.notification.DiscordMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchRunner implements ApplicationRunner {
    private final ScoreJobLauncher scoreJobLauncher;
    private final DiscordBatchNotifier notifier;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        notifier.send(DiscordMessage.ofStart());
        scoreJobLauncher.launch();
        notifier.send(DiscordMessage.ofEnd());
    }
}
