package org.woehlke.tools.model.common.impl;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.common.JobEvent;
import org.woehlke.tools.model.common.JobEventService;
import org.woehlke.tools.model.common.JobEventServiceAsync;

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
