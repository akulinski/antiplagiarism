package com.antiplagiarism.filecheckservice.core.services;

import com.antiplagiarism.filecheckservice.domain.events.ConvertByteToStringEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Service
public class ByteToStringService {

    private final EventBus eventBus;

    @Autowired
    public ByteToStringService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void registerToEventBus(){
        this.eventBus.register(this);
    }

    @Subscribe
    public void handleConvertByteToStringEvent(ConvertByteToStringEvent convertByteToStringEvent){
        String converted = new String(convertByteToStringEvent.getDocumentData(), StandardCharsets.UTF_8);
    }

}
