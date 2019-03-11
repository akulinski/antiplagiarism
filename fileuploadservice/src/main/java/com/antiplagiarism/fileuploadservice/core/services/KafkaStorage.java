package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import domain.DocumentDTO;
import domain.events.DocumentAddedKafkaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component("kafkaStorage")
public class KafkaStorage implements IStorageService {

    @Value(value = "${kafka.topic}")
    private String kafkaTopic;

    private final KafkaTemplate<String, DocumentAddedKafkaEvent> kafkaTemplate;

    @Autowired
    public KafkaStorage(KafkaTemplate<String, DocumentAddedKafkaEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    @Async
    public void save(SaveDocumentEvent saveDocumentEvent) throws IOException {
        DocumentAddedKafkaEvent documentAddedKafkaEvent = new DocumentAddedKafkaEvent();
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setTitle(saveDocumentEvent.getFileName());
        documentAddedKafkaEvent.setDocumentDTO(documentDTO);
        documentAddedKafkaEvent.setDocumentData(saveDocumentEvent.getBytes());
        kafkaTemplate.send(kafkaTopic, documentAddedKafkaEvent);
    }
}
