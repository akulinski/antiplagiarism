package com.antiplagiarism.filecheckservice.config;

import com.antiplagiarism.filecheckservice.domain.events.ConvertByteToStringEvent;
import com.antiplagiarism.fileuploadservice.domain.events.DocumentAddedKafkaEvent;
import com.google.common.eventbus.EventBus;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Log4j2
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    private final EventBus eventBus;

    @Autowired
    public KafkaConsumerConfig(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Bean
    public ConsumerFactory<String, DocumentAddedKafkaEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "antiplagiarism");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(DocumentAddedKafkaEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DocumentAddedKafkaEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, DocumentAddedKafkaEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "antiplagiarism", groupId = "antiplagiarism")
    public void listen(DocumentAddedKafkaEvent documentAddedKafkaEvent) {
        log.debug("Got message of Class: "+documentAddedKafkaEvent.getClass().getSimpleName());
        ConvertByteToStringEvent event = new ConvertByteToStringEvent();
        event.setDocumentData(documentAddedKafkaEvent.getDocumentData());
        event.setDocumentDTO(documentAddedKafkaEvent.getDocumentDTO());
        eventBus.post(event);
    }
}
