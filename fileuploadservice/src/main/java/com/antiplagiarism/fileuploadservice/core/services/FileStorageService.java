package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;
import com.antiplagiarism.fileuploadservice.domain.events.SaveDocumentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service("fileStorage")
public class FileStorageService implements IStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @Override
    @Transactional
    @Async
    public void save(SaveDocumentEvent saveDocumentEvent) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(saveDocumentEvent.getFileName()));

        if (fileName.contains("..")) {
            throw new IllegalStateException(FileExceptions.SORRY_FILENAME_CONTAINS_INVALID_PATH_SEQUENCE.getValue() + fileName);
        }

        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        if (targetLocation.toFile().exists()) {
            throw new IllegalStateException(FileExceptions.FILE_WITH_THAT_NAME_ALREADY_EXISTS.getValue());
        }

        Files.copy(saveDocumentEvent.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    }
}
