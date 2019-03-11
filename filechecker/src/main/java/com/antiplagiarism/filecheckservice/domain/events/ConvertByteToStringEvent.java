package com.antiplagiarism.filecheckservice.domain.events;

import domain.DocumentDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConvertByteToStringEvent {
    DocumentDTO documentDTO;
    byte[] documentData;

}
