package com.antiplagiarism.fileuploadservice;

import com.antiplagiarism.fileuploadservice.config.ApplicationProperties;
import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, FileStorageProperties.class})
@EnableAsync
@EntityScan(basePackages = {"com.antiplagiarism.fileuploadservice.domain.entites"})
public class FileuploadserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileuploadserviceApplication.class, args);
    }
}
