package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class SaveDocumentEventListenerService {

    private final EventBus eventBus;

    private final IStorageService fileStorage;

    private final IStorageService databaseStorage;

    private final IStorageService kafkaStorage;


    @Autowired
    public SaveDocumentEventListenerService(EventBus eventBus, @Qualifier("fileStorage") IStorageService fileStorage, @Qualifier("databaseStorage") IStorageService databaseStorage, @Qualifier("kafkaStorage") IStorageService kafkaStorage) {
        this.eventBus = eventBus;
        this.fileStorage = fileStorage;
        this.databaseStorage = databaseStorage;
        this.kafkaStorage = kafkaStorage;
    }

    @PostConstruct
    public void subscribeToEventBus() {
        this.eventBus.register(this);
    }

    @Subscribe
    @Transactional
    public void onSaveDocumentEvent(SaveDocumentEvent saveDocumentEvent) throws IOException {
        saveInNewThread(fileStorage, saveDocumentEvent);
        saveInNewThread(databaseStorage, saveDocumentEvent);
        saveInNewThread(kafkaStorage, saveDocumentEvent);
    }

    private void saveInNewThread(IStorageService iStorageService, SaveDocumentEvent saveDocumentEvent) {
        new Thread(() -> {
            try {
                iStorageService.save(saveDocumentEvent.getMultipartFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
