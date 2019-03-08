package com.antiplagiarism.fileuploadservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DocumentDTO {
    private String title;

    private Date creationDate;

    private Date modificationDate;


    @Override
    public String toString() {
        return "DocumentDTO{" +
                "title='" + title + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
