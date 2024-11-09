package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Lesson4Application {
    public static void main(String[] args) {
        SpringApplication.run(Lesson4Application.class, args);
    }
}
