package com.antiplagiarism.filecheckservice.core.services;

import com.antiplagiarism.filecheckservice.domain.events.ConvertByteToStringEvent;
import com.antiplagiarism.filecheckservice.domain.events.SplitTextEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Log4j2
public class ByteToStringService {

    private final EventBus eventBus;

    @Autowired
    public ByteToStringService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void registerToEventBus() {
        this.eventBus.register(this);

    }

    @Subscribe
    public void handleConvertByteToStringEvent(ConvertByteToStringEvent convertByteToStringEvent) {
        InputStream inputStream = new ByteArrayInputStream(convertByteToStringEvent.getDocumentData());
        Tika tika = new Tika();
        try {
            String extractedText = tika.parseToString(inputStream);
            this.eventBus.post(new SplitTextEvent(extractedText,convertByteToStringEvent.getDocumentDTO().getTitle()));
        } catch (IOException | TikaException e) {
            log.error(e.getLocalizedMessage());
        }

    }
}
