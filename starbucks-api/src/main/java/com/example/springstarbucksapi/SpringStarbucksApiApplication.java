package com.example.springstarbucksapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringStarbucksApiApplication {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SpringStarbucksApiApplication.class);
        // Log MYSQL_HOST from application.properties
        logger.info("Environment variables:");
        logger.info("MYSQL_HOST: " + System.getenv("MYSQL_HOST"));
        SpringApplication.run(SpringStarbucksApiApplication.class, args);
    }
}

