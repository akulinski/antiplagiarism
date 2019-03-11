package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.core.repositories.DocumentRepository;
import com.antiplagiarism.fileuploadservice.domain.entites.DocumentEntity;
import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service("databaseStorage")
public class DatabaseStorageService implements IStorageService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DatabaseStorageService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    @Transactional
    @Async
    public void save(SaveDocumentEvent saveDocumentEvent) throws IOException {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitle(saveDocumentEvent.getFileName());
        documentEntity.setBytes(saveDocumentEvent.getBytes());
        documentRepository.save(documentEntity);
    }
}
