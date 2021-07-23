package com.smartech.i2019.adaptiveinterviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AdaptiveInterviewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdaptiveInterviewsApplication.class, args);
    }
}