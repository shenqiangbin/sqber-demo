package com.sqber.log4jdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sqber.log4jdemo, com.sqber.commonWeb")
public class Log4jDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(Log4jDemoApplication.class, args);
    }
}
