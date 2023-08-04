package com.sqber.log4jdemo.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class KafkaConfig {

    @Value("${kafka.zookeeper}")
    private String zookeeper;

    @Value("${kafka.broker}")
    private String broker;

}
