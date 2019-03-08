package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.DocumentDTO;
import com.antiplagiarism.fileuploadservice.domain.events.DocumentAddedKafkaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public void save(MultipartFile multipartFile) throws IOException {
        DocumentAddedKafkaEvent documentAddedKafkaEvent = new DocumentAddedKafkaEvent();
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setTitle(multipartFile.getName());
        documentAddedKafkaEvent.setDocumentDTO(documentDTO);
        documentAddedKafkaEvent.setDocumentData(multipartFile.getBytes());
        kafkaTemplate.send(kafkaTopic,documentAddedKafkaEvent);
    }
}
