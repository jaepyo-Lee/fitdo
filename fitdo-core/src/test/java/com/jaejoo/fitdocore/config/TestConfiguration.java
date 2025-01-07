package com.jaejoo.fitdocore.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.jaejoo.fitdocore",
        "com.jaejoo.fitdomysql",
        "com.jaejoo.fitdoutil",
        "com.jaejoo.fitdoredis"})
@ComponentScan(basePackages = "com.jaejoo")
public class TestConfiguration {
}
