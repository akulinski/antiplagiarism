package com.antiplagiarism.fileuploadservice.core.repositories;

import com.antiplagiarism.fileuploadservice.domain.entites.DocumentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<DocumentEntity, Integer> {
}
