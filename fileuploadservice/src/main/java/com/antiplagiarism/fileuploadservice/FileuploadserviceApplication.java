package com.antiplagiarism.fileuploadservice;

import com.antiplagiarism.fileuploadservice.config.ApplicationProperties;
import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class, FileStorageProperties.class})
public class FileuploadserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileuploadserviceApplication.class, args);
    }

}
