package com.antiplagiarism.fileuploadservice;

import com.antiplagiarism.fileuploadservice.config.ApplicationProperties;
import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, FileStorageProperties.class})
@EnableAsync
@EntityScan(basePackages = {"com.antiplagiarism.fileuploadservice.domain.entites"})
@EnableEurekaClient
public class FileuploadserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileuploadserviceApplication.class, args);
    }
}
