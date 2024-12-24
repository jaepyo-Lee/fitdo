package com.jaejoo.fitdobatch.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class DiscordBatchNotifier {

    @Value("${discord.webhook.uri}")
    private String DISCORD_WEBHOOK_URI;
    private final WebClient webClient;


    public DiscordBatchNotifier() {
        this.webClient = WebClient.create("https://discord.com");
    }

    public void send(Object sendMessage) throws InterruptedException {
        webClient.post()
                .uri(DISCORD_WEBHOOK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(sendMessage)
                .retrieve() // 응답을 처리하는 단계 추가
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("전송 성공: " + response))
                .doOnError(error -> System.err.println("전송 실패: " + error.getMessage()))
                .subscribe(); // 요청 실행
    }
}

