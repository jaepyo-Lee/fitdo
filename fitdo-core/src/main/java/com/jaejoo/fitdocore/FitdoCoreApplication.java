package com.jaejoo.fitdocore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.jaejoo.fitdocore" , "com.jaejoo.fitdomysql", "com.jaejoo.fitdoutil","com.jaejoo.fitdoredis"})
public class FitdoCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitdoCoreApplication.class, args);
	}

}
