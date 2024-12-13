package com.jaejoo.fitdobatch.controller;

import com.jaejoo.fitdobatch.batch.launcher.DailyScoreUpdateJobLauncher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BatchController {
    private final DailyScoreUpdateJobLauncher dailyScoreUpdateJobLauncher;

    @GetMapping("/api/v1/score-batch")
    public String executeBatch() throws Exception {
        dailyScoreUpdateJobLauncher.launch();
        return "success";
    }

}
