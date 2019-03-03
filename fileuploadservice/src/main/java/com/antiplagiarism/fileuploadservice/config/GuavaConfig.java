package com.antiplagiarism.fileuploadservice.config;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GuavaConfig {

    @Bean
    public EventBus getEventBus() {
        return new EventBus();
    }
}
