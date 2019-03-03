package com.antiplagiarism.fileuploadservice.core.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IStorageService {

    void save(MultipartFile multipartFile) throws IOException;

}
