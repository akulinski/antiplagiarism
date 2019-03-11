package com.antiplagiarism.filecheckservice.domain.events;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class SplitTextEvent {
    private String text;
}
