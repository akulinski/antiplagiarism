package com.antiplagiarism.fileuploadservice.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDocumentEvent {
    private String fileName;
    private byte[] bytes;
    private InputStream inputStream;
}
