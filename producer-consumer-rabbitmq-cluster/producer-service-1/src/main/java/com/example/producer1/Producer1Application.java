package com.example.producer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Producer1Application {
    public static void main(String[] args) {
        SpringApplication.run(Producer1Application.class, args);
    }
}