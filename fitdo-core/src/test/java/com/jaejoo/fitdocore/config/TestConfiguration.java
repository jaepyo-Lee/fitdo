package com.jaejoo.fitdocore.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.jaejoo.fitdocore" , "com.jaejoo.fitdomysql", "com.jaejoo.fitdoutil","com.jaejoo.fitdoredis"})
//@EnableJpaRepositories(basePackages = "com.jaejoo.fitdomysql")
public class TestConfiguration {
}
