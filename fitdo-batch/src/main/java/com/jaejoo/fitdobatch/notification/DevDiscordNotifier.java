package com.jaejoo.fitdobatch.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile({"dev"})
public class DevDiscordNotifier implements DiscordNotification {

    @Value("${discord.webhook.uri}")
    private String DISCORD_WEBHOOK_URI;
    private final DiscordClient client;


    public void send(Object sendMessage) {
        System.out.println("디코..보냈어");
        String[] discord_impo = DISCORD_WEBHOOK_URI.split("/");
        client.sendAlarm(discord_impo[0], discord_impo[1],sendMessage);
    }
}

