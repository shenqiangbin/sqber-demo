package com.sqber.dbdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sqber.dbdemo", "com.sqber.commonWeb"})
public class DbDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbDemoApplication.class, args);
    }
}
