package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.core.repositories.DocumentRepository;
import com.antiplagiarism.fileuploadservice.domain.entites.DocumentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service("databaseStorage")
public class DatabaseStorageService implements IStorageService{

    private final DocumentRepository documentRepository;

    @Autowired
    public DatabaseStorageService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    @Transactional
    public void save(MultipartFile multipartFile) throws IOException {

        if(multipartFile.getBytes() == null){
            throw new IOException("No content of file");
        }

        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setTitle(multipartFile.getName());
        documentEntity.setBytes(multipartFile.getBytes());
        documentRepository.save(documentEntity);
    }
}
