package org.woehlke.tools.model.db.services.impl;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.model.db.services.JobEventService;
import org.woehlke.tools.model.db.services.JobEventServiceAsync;

@NoRepositoryBean
public abstract class JobEventServiceAsyncImpl<T extends JobEvent> implements JobEventServiceAsync<T> {

    private final JobEventService<T> jobEventService;

    public JobEventServiceAsyncImpl(JobEventService<T> jobEventService) {
        this.jobEventService = jobEventService;
    }

    @Async
    @Override
    public T add(T p) {
        return this.jobEventService.add(p);
    }
}
