package com.antiplagiarism.filecheckservice.core.services;

import com.antiplagiarism.filecheckservice.core.interfaces.DocumentPartRepository;
import com.antiplagiarism.filecheckservice.domain.documents.DocumentPart;
import com.antiplagiarism.filecheckservice.domain.events.SplitTextEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@Log4j2
public class TextSplittingService {
    private final EventBus eventBus;

    private final DocumentPartRepository partRepository;

    @Autowired
    public TextSplittingService(EventBus eventBus, DocumentPartRepository partRepository) {
        this.eventBus = eventBus;
        this.partRepository = partRepository;
    }

    @PostConstruct
    public void subscribeToEventBus() {
        eventBus.register(this);
    }

    @Subscribe
    public void handleSplittingEvent(SplitTextEvent splitTextEvent) {
        String[] splitArray = splitTextEvent.getText().split("\\.");

        for (String str:splitArray){
            DocumentPart documentPart = new DocumentPart();
            documentPart.setPart(str);
            documentPart.setFileName(splitTextEvent.getFileName());
            partRepository.save(documentPart);
        }
    }
}
