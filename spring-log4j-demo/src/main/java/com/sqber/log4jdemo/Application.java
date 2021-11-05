package com.sqber.log4jdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sqber.log4jdemo, com.sqber.commonWeb")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
