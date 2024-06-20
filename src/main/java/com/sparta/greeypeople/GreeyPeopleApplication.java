package com.sparta.greeypeople;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GreeyPeopleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeyPeopleApplication.class, args);
    }

}
