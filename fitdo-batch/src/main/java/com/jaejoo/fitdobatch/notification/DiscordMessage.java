package com.jaejoo.fitdobatch.notification;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DiscordMessage {
    private String content;
    private List<Embed> embeds;

    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class Embed {

        private String title;
        private String description;
    }

    public static DiscordMessage ofStart() {
        return DiscordMessage.builder()
                .embeds(List.of(DiscordMessage.Embed.builder()
                        .title("Lambda-Batch-Start")
                        .description("### 🕖 발생 시간\n"
                                + LocalDateTime.now()
                                + "\n```")
                        .build()))
                .content("# 🚨 배치 람다 시이이이이ㅣㅇ작 🚨")
                .build();
    }

    public static DiscordMessage ofEnd() {
        return DiscordMessage.builder()
                .embeds(List.of(DiscordMessage.Embed.builder()
                        .title("Lambda-Batch-End")
                        .description("### 🕖 발생 시간\n"
                                + LocalDateTime.now()
                                + "\n```")
                        .build()))
                .content("# 🚨 배치 람다 끄으으으으읕 🚨")
                .build();
    }
}
