package com.antiplagiarism.fileuploadservice.core.services;

import com.antiplagiarism.fileuploadservice.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
    public void save(MultipartFile multipartFile) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try {

            if (fileName.contains("..")) {
                throw new IllegalStateException(FileExceptions.SORRY_FILENAME_CONTAINS_INVALID_PATH_SEQUENCE.getValue() + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);

            if(targetLocation.toFile().exists()){
                throw new IllegalStateException(FileExceptions.FILE_WITH_THAT_NAME_ALREADY_EXISTS.getValue());
            }

            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
            throw new IllegalStateException(FileExceptions.COULD_NOT_STORE_FILE.getValue() + fileName + FileExceptions.PLEASE_TRY_AGAIN.getValue(), ex);
        }
    }
}
