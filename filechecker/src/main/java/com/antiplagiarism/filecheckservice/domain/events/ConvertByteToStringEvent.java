package com.antiplagiarism.filecheckservice.domain.events;

import com.antiplagiarism.fileuploadservice.domain.DocumentDTO;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ConvertByteToStringEvent {
    DocumentDTO documentDTO;
    byte[] documentData;

}
