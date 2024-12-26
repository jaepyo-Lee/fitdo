package com.jaejoo.fitdobatch.runner;

import com.jaejoo.fitdobatch.launcher.ScoreJobLauncher;
import com.jaejoo.fitdobatch.notification.DevDiscordNotifier;
import com.jaejoo.fitdobatch.notification.DiscordMessage;
import com.jaejoo.fitdobatch.notification.DiscordNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchRunner implements ApplicationRunner {
    private final ScoreJobLauncher scoreJobLauncher;
    private final DiscordNotification notifier;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        notifier.send(DiscordMessage.ofStart());
        Thread.sleep(1000);
        scoreJobLauncher.launch();
        notifier.send(DiscordMessage.ofEnd());
        Thread.sleep(1000);
    }
}
