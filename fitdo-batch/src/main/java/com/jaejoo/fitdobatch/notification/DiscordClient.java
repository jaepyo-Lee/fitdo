package com.jaejoo.fitdobatch.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "discordApi", url = "https://discord.com")
public interface DiscordClient {
    @PostMapping("/api/webhooks/{discord-uuid}/{discord-key}")
    void sendAlarm(
            @PathVariable(name = "discord-uuid") String uuid,
            @PathVariable(name = "discord-key") String key,
            @RequestBody Object sendMessage);
}
