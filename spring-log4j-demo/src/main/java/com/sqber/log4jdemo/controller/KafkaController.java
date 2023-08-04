package com.sqber.log4jdemo.controller;

import com.sqber.commonWeb.R;
import com.sqber.log4jdemo.config.KafkaConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

@RestController
public class KafkaController {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private MinioClient minioClient;

    @GetMapping("/kafka")
    public R index() {
        return R.success("kafka success");
    }

    @GetMapping("/kafka/config")
    public R config() {
        return R.success("ok:" + kafkaConfig.getZookeeper() + ";" + kafkaConfig.getBroker());
    }

    @GetMapping("/kafka/test1")
    public R test1() {

        String msg = new Date().toString();

        KafkaProducer producer = createProducer();
        producer.send(new ProducerRecord<String, String>("test1", "message: " + msg));

        return R.success("send ok:" + msg);
    }

    @GetMapping("/kafka/test2")
    public R test2() {

        KafkaConsumer consumer = createConsumer();
        consumer.subscribe(Arrays.asList("test1"));

        ConsumerRecords<String, String> msgList = consumer.poll(5);
        if (null != msgList && msgList.count() > 0) {
            for (ConsumerRecord<String, String> record : msgList) {
                System.out.println("=======receive: key = " + record.key() + ", value = " + record.value() + " offset===" + record.offset());
            }
        }

        return R.success("receive ok");
    }

    private KafkaProducer createProducer() {
        Properties props = new Properties();
        //props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092");
        props.put("bootstrap.servers", kafkaConfig.getBroker());
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        return producer;
    }


    private KafkaConsumer createConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConfig.getBroker());
        props.put("group.id", "groupA");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        return new KafkaConsumer<String, String>(props);
    }
}
