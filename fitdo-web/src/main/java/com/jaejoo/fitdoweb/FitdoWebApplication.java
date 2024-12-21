package com.jaejoo.fitdoweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jaejoo.fitdocore", "com.jaejoo.fitdoweb", "com.jaejoo.fitdoutil"})
public class FitdoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitdoWebApplication.class, args);
    }

}
