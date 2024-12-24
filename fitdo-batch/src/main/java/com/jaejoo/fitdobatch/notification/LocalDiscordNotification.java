package com.jaejoo.fitdobatch.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocalDiscordNotification implements DiscordNotification {
    @Override
    public void send(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(o));
    }
}
