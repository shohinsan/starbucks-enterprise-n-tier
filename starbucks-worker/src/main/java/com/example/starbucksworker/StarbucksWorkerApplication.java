package com.example.starbucksworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StarbucksWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarbucksWorkerApplication.class, args);
	}

}
