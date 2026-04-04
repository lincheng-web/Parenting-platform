package com.parenting.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.parenting"})
@EnableJpaRepositories(basePackages = {"com.parenting.core.repository"})
@EntityScan(basePackages = {"com.parenting.core.entity"})
@EnableScheduling
public class ParentingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParentingApplication.class, args);
    }
}
