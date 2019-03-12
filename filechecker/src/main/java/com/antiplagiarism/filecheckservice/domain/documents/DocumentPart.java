package com.antiplagiarism.filecheckservice.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Document(indexName = "part_index", type = "part")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentPart {

    @Id
    private String documentId;

    private String authorsName;

    private String part;

    private Date creationDate = new Date();

    private String fileName;


}
