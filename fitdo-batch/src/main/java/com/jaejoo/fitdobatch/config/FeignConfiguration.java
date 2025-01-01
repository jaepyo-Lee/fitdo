package com.jaejoo.fitdobatch.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.jaejoo.fitdobatch")
public class FeignConfiguration {
}
