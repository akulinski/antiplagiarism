package com.antiplagiarism.fileuploadservice.core.services;

import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

public interface IStorageService {

    @Transactional
    void save(MultipartFile multipartFile) throws IOException;

}
