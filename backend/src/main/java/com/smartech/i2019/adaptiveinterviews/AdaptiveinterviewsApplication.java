package com.smartech.i2019.adaptiveinterviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
public class AdaptiveinterviewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdaptiveinterviewsApplication.class, args);
    }
}
