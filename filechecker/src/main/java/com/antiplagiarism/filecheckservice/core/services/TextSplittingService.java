package com.antiplagiarism.filecheckservice.core.services;

import com.antiplagiarism.filecheckservice.domain.events.SplitTextEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Log4j2
public class TextSplittingService {
    private final EventBus eventBus;

    @Autowired
    public TextSplittingService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void subscribeToEventBus() {
        eventBus.register(this);
    }

    @Subscribe
    public void handleSplittingEvent(SplitTextEvent splitTextEvent) {
        log.debug(splitTextEvent.getText());
    }
}
