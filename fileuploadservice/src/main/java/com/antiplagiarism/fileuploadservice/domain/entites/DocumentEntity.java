package com.antiplagiarism.fileuploadservice.domain.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Document")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "bytes",columnDefinition = "NVARCHAR(MAX)")
    private byte[] bytes;

    @Column
    @CreatedDate
    private Date creationDate;

    @Column
    @LastModifiedDate
    private Date modificationDate;

}
