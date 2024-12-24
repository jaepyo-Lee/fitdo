package com.jaejoo.fitdobatch.notification;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface DiscordNotification {
    void send(Object o) throws JsonProcessingException;
}
