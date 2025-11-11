package com.ecoembes.e9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcoembesE9Application {
    public static void main(String[] args) {
        SpringApplication.run(EcoembesE9Application.class, args);
    }
}
