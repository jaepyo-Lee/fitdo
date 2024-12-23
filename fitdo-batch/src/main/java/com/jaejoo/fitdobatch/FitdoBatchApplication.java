package com.jaejoo.fitdobatch;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(/*scanBasePackages = {"com.jaejoo.fitdobatch",
"com.jaejoo.fitdomysql.domain.user.repository.jpa.entity",
"com.jaejoo.fitdomysql.domain.exercise.infra.repository.jpa.entity",
"com.jaejoo.fitdoredis"}*/)
public class FitdoBatchApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(FitdoBatchApplication.class, args);
        final int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
        SpringApplication.run(FitdoBatchApplication.class, args);

    }

}
