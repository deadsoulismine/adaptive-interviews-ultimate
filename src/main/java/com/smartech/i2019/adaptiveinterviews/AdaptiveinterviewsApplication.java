package com.smartech.i2019.adaptiveinterviews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class AdaptiveinterviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdaptiveinterviewsApplication.class, args);
    }

}
