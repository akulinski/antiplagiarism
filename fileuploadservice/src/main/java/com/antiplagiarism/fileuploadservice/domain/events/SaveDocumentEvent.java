package com.antiplagiarism.fileuploadservice.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaveDocumentEvent {
    private MultipartFile multipartFile;
}
