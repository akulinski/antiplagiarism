package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SaveDocumentEventListenerService {

    private final EventBus eventBus;

    private final IStorageService fileStorage;

    private final IStorageService databaseStorage;

    @Autowired
    public SaveDocumentEventListenerService(EventBus eventBus, @Qualifier("fileStorage") IStorageService fileStorage, @Qualifier("databaseStorage") IStorageService databaseStorage) {
        this.eventBus = eventBus;
        this.fileStorage = fileStorage;
        this.databaseStorage = databaseStorage;
    }

    @PostConstruct
    public void subscribeToEventBus() {
        this.eventBus.register(this);
    }

    @Subscribe
    public void onSaveDocumentEvent(SaveDocumentEvent saveDocumentEvent) {
        fileStorage.save(saveDocumentEvent.getMultipartFile());
        databaseStorage.save(saveDocumentEvent.getMultipartFile());
    }
}
