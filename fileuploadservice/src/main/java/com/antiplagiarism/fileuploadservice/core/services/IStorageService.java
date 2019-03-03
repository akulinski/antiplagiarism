package com.antiplagiarism.fileuploadservice.core.services;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    void save(MultipartFile multipartFile);

}
