package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.DocumentDTO;
import com.antiplagiarism.fileuploadservice.domain.events.DocumentAddedKafkaEvent;
import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class SaveDocumentEventListenerService {

    private final EventBus eventBus;

    private final IStorageService fileStorage;

    private final IStorageService databaseStorage;

    private final KafkaTemplate<String, DocumentAddedKafkaEvent> kafkaTemplate;

    @Autowired
    public SaveDocumentEventListenerService(EventBus eventBus, @Qualifier("fileStorage") IStorageService fileStorage, @Qualifier("databaseStorage") IStorageService databaseStorage, KafkaTemplate<String, DocumentAddedKafkaEvent> kafkaTemplate) {
        this.eventBus = eventBus;
        this.fileStorage = fileStorage;
        this.databaseStorage = databaseStorage;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void subscribeToEventBus() {
        this.eventBus.register(this);
    }

    //TODO multithreding
    @Subscribe
    @Transactional
    public void onSaveDocumentEvent(SaveDocumentEvent saveDocumentEvent) throws IOException {
        fileStorage.save(saveDocumentEvent.getMultipartFile());
        databaseStorage.save(saveDocumentEvent.getMultipartFile());
        
        DocumentAddedKafkaEvent documentAddedKafkaEvent = new DocumentAddedKafkaEvent();
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setTitle(saveDocumentEvent.getMultipartFile().getName());
        documentAddedKafkaEvent.setDocumentDTO(documentDTO);
        documentAddedKafkaEvent.setDocumentData(saveDocumentEvent.getMultipartFile().getBytes());
        kafkaTemplate.send("antiplagiarism",documentAddedKafkaEvent);
    }
}
