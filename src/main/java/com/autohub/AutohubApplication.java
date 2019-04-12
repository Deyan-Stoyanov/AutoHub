package com.autohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AutohubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutohubApplication.class, args);
    }

}
