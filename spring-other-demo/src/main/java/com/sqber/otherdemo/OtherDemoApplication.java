package com.sqber.otherdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sqber.otherdemo", "com.sqber.commonWeb"})
public class OtherDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OtherDemoApplication.class, args);
    }
}
