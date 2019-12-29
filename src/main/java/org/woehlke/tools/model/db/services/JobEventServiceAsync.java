package org.woehlke.tools.model.db.services;

import org.springframework.scheduling.annotation.Async;

import org.woehlke.tools.model.db.entities.JobEvent;

public interface JobEventServiceAsync<T extends JobEvent> {

    @Async
    T add(T p);

}
