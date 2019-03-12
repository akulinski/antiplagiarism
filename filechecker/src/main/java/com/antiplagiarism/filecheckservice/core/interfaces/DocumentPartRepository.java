package com.antiplagiarism.filecheckservice.core.interfaces;

import com.antiplagiarism.filecheckservice.domain.documents.DocumentPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentPartRepository extends ElasticsearchRepository<DocumentPart, String> {

    Page<DocumentPart> findByAuthorsName(String name, Pageable pageable);

    List<DocumentPart> findByPart(String part);

}
