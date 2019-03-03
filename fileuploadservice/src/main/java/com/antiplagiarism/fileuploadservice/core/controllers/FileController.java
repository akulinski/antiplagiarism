package com.antiplagiarism.fileuploadservice.core.controllers;

import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private static final String FILE_UPLOADED_SUCCESSFULLY = "File Uploaded successfully";

    private final EventBus eventBus;

    @Autowired
    public FileController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostMapping("/upload/document")
    public ResponseEntity uploadDocument(@RequestParam("document") MultipartFile document) {
        eventBus.post(new SaveDocumentEvent(document));
        return ResponseEntity.ok(FILE_UPLOADED_SUCCESSFULLY);
    }
}
