package com.sqber.iyou;

import com.sqber.iyou.service.QuanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@ComponentScan({"com.sqber.iyou", "com.sqber.commonWeb"})
public class IyouApplication {

    @Autowired
    QuanService quanService;

    public static void main(String[] args) {
        SpringApplication.run(IyouApplication.class, args);
    }

    @PostConstruct
    public void quanStart() throws IOException, InterruptedException {
        quanService.start();
    }
}
