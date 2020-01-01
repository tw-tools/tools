package org.woehlke.tools.model.common;

import org.springframework.scheduling.annotation.Async;

public interface JobEventServiceAsync<T extends JobEvent> {

    @Async
    T add(T p);

    void sendMessage(T p);
}
