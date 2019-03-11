package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

public interface IStorageService {

    @Transactional
    @Async
    void save(SaveDocumentEvent multipartFile) throws IOException;

}
