package com.jaejoo.fitdobatch.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DiscordNotificationConfiguration {
    @Profile({"local","default"})
    @Bean
    public DiscordNotification local(){
        return new LocalDiscordNotification();
    }

    @Profile("dev")
    @Bean
    public DiscordNotification dev(){
        return new DevDiscordNotifier();
    }
}
